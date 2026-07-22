package com.iot.green.house.mqtt.listener;

import com.iot.green.house.dto.SensorNotification;
import com.iot.green.house.enums.MeasurementType;
import com.iot.green.house.enums.MeasurementUnit;
import com.iot.green.house.enums.SensorType;
import com.iot.green.house.model.Measurement;
import com.iot.green.house.model.Sensor;
import com.iot.green.house.repository.MeasurementRepository;
import com.iot.green.house.repository.SensorRepository;
import com.iot.green.house.service.WebSocketNotificationService;
import com.iot.green.house.utils.SensorLiveState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.iot.green.house.enums.MeasurementType.*;

@Slf4j
@Component
@AllArgsConstructor
public class MqttListener {

    private final SensorRepository sensorRepo;
    private final MeasurementRepository measurementRepo;
    private final SensorLiveState liveState;
    private final WebSocketNotificationService webSocketNotificationService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<Long, Double> lastSavedValues = new ConcurrentHashMap<>();

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handle(Message<?> message) {

        String topic = message.getHeaders()
                .get("mqtt_receivedTopic")
                .toString();

        String payload = message.getPayload().toString();

        log.info("payload {}", payload);
        log.info("topic {}", topic);

        try {

            JsonNode node = mapper.readTree(payload);

            // 📡 1. REGISTER SENSOR
            if (topic.equals("greenhouse/sensor/register")) {

                String name = node.get("name").asText();
                String location = node.get("location").asText();
                String model = node.get("model").asText();
                String type = node.get("type").asText();

                Sensor s = new Sensor();

                s.setName(name);
                s.setLocation(location);
                s.setModel(model);
                s.setType(SensorType.valueOf(type));

                sensorRepo.save(s);

                log.info("New sensor created: {}", name);
            }

            // 🌡️ 2. SENSOR DATA
            else if (topic.equals("greenhouse/sensor/data")) {
                    // SOS: Το διαβάζουμε ως String πλέον (π.χ. "arduino_sensor_5")
                    Long deviceUid = node.get("deviceId").asLong();
                    Double value = node.get("value").asDouble();

                    // 🔥 ΑΛΛΑΓΗ 1: Ενημερώνουμε τη RAM ακαριαία για το Angular UI!
                    // Αυτό τρέχει κάθε 2-5 δευτερόλεπτα χωρίς να αγγίζει την SD κάρτα.
                    liveState.updateValue(deviceUid, value);
                    log.info("🔴 Live RAM Update: {} = {}", deviceUid, value);

                    // 🔥 ΑΛΛΑΓΗ 2: Έλεγχος αν άλλαξε η τιμή σε σχέση με την προηγούμενη αποθηκευμένη
                    Double lastSavedValue = lastSavedValues.get(deviceUid);

                    // Αν δεν υπάρχει προηγούμενη τιμή (π.χ. μόλις ξεκίνησε ο server)
                    // Ή αν η νέα τιμή είναι διαφορετική από την παλιά
                    if (lastSavedValue == null || !lastSavedValue.equals(value)) {

                        // Ψάχνουμε τον αισθητήρα στη βάση με το String deviceUid
                        Sensor sensor = sensorRepo.findById(deviceUid)
                                .orElseThrow(() -> new RuntimeException("Sensor not found: " + deviceUid));

                        MeasurementType SensorType = mapSensorToMeasurement(sensor.getType());
                        MeasurementUnit SensorUnit = mapSensorToUnit(sensor.getType());
                        Measurement m = new Measurement();
                        m.setSensor(sensor);
                        m.setValue(value);
                        m.setTimestamp(LocalDateTime.now());
                        m.setType(SensorType);
                        m.setUnit(SensorUnit);

                        // Γράφει στη Βάση Δεδομένων ΜΟΝΟ όταν αλλάξει η τιμή
                        measurementRepo.save(m);
                        webSocketNotificationService.sendSensorAlert(
                                createNotification(sensor, value)
                        );
                        // Ενημερώνουμε το Map στη RAM με τη νέα τιμή που μόλις αποθηκεύτηκε
                        lastSavedValues.put(deviceUid, value);

                        log.info("💾 [DATABASE WRITE] Value changed! Saved new historical measurement: {} for {}", value, deviceUid);
                    } else {
                        // Αν η τιμή είναι ολόιδια, απλά δεν κάνουμε τίποτα (δεν επιβαρύνουμε τη βάση)
                        log.info("⏭️ [DB WRITE SKIPPED] Value {} for {} has not changed. Skipping insert.", value, deviceUid);
                    }
            }

        } catch(Exception e){
            log.error("MQTT error", e);
        }
    }

    private MeasurementType mapSensorToMeasurement(SensorType type) {
        return switch (type) {
            case TEMPERATURE -> MeasurementType.TEMPERATURE;
            case HUMIDITY -> MeasurementType.HUMIDITY;
            case WATER -> WATER_LEVEL;
            case LIGHT -> LIGHT_INTENSITY;
            case MOISTURE -> SOLID;
        };
    }

    private MeasurementUnit mapSensorToUnit(SensorType type) {
        return switch (type) {
            case TEMPERATURE -> MeasurementUnit.CELSIUS;
            case HUMIDITY -> MeasurementUnit.PERCENT;
            case WATER -> MeasurementUnit.LITERS;
            case LIGHT -> MeasurementUnit.LUX;
            case MOISTURE -> MeasurementUnit.PERCENT;
        };
    }

    private SensorNotification createNotification(
            Sensor sensor,
            Double value
    ) {
        String type;
        String message;

        switch (sensor.getType()) {
            case TEMPERATURE -> {
                if (value > 40) {
                    type = "TEMPERATURE_ALERT";
                    message =
                            "Temperature reached "
                                    + value
                                    + " °C";

                } else if (value < 15) {
                    type = "TEMPERATURE_ALERT";
                    message =
                            "Temperature dropped to "
                                    + value
                                    + " °C";

                } else {
                    type = "TEMPERATURE_INFO";
                    message =
                            "Temperature is "
                                    + value
                                    + " °C";
                }
            }
            case HUMIDITY -> {
                if (value < 30) {
                    type = "HUMIDITY_ALERT";
                    message =
                            "Humidity dropped to "
                                    + value
                                    + " %";
                } else if (value > 90) {
                    type = "HUMIDITY_ALERT";
                    message =
                            "Humidity reached "
                                    + value
                                    + " %";
                } else {
                    type = "HUMIDITY_INFO";
                    message =
                            "Humidity is "
                                    + value
                                    + " %";
                }
            }
            case MOISTURE -> {
                if (value < 50) {
                    type = "SOIL_ALERT";
                    message =
                            "Soil moisture dropped to "
                                    + value
                                    + " %";

                } else {
                    type = "SOIL_INFO";
                    message =
                            "Soil moisture is "
                                    + value
                                    + " %";
                }
            }
            default -> {
                type = "INFO";
                message =
                        "Sensor value: "
                                + value;
            }
        }
        return new SensorNotification(
                type,
                sensor.getName(),
                value,
                message
        );
    }
}