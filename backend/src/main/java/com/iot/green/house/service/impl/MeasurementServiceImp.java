package com.iot.green.house.service.impl;

import com.iot.green.house.enums.MeasurementType;
import com.iot.green.house.enums.MeasurementUnit;
import com.iot.green.house.enums.SensorType;
import com.iot.green.house.model.Measurement;
import com.iot.green.house.model.Sensor;
import com.iot.green.house.repository.MeasurementRepository;
import com.iot.green.house.repository.SensorRepository;
import com.iot.green.house.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MeasurementServiceImp  implements MeasurementService {

    private final SensorRepository sensorRepo;
    private final MeasurementRepository measurementRepository;

    // 📌 GET ALL
    @Override
    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    // 📌 GET BY ID
    @Override
    public Measurement getById(Long id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Measurement not found: " + id));
    }

    // 📌 GET BY SENSOR
    @Override
    public List<Measurement> getBySensorId(Long sensorId) {

        return measurementRepository.findBySensorId(sensorId);
    }

    // 📌 GET LAST MEASUREMENT PER SENSOR
    @Override
    public Measurement getLastBySensor(Long sensorId) {

        return measurementRepository
                .findTopBySensorIdOrderByTimestampDesc(sensorId)
                .orElseThrow(() -> new RuntimeException("No measurements found"));
    }
    @Override
    public Optional<LocalDateTime> getLastMeasurementTime(Long sensorId) {
        return measurementRepository.findLastMeasurementTime(sensorId);
    }

    // 📌 GET BY DATE RANGE
    @Override
    public List<Measurement> getByDateRange(LocalDateTime from, LocalDateTime to) {

        return measurementRepository.findByTimestampBetween(from, to);
    }

    // 📌 DELETE
    @Override
    public void delete(Long id) {
        measurementRepository.deleteById(id);
    }
    @Override
    public void saveMeasurement(long deviceId, Double value) {

        Sensor sensor = sensorRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Sensor not found: " + deviceId));

        Measurement m = new Measurement();

        m.setType(mapSensorToMeasurement(sensor.getType()));
        m.setUnit(mapSensorToUnit(sensor.getType()));

        m.setValue(value);
        m.setTimestamp(LocalDateTime.now());
        m.setSensor(sensor);

        measurementRepository.save(m);
    }

    private MeasurementType mapSensorToMeasurement(SensorType type) {
        return switch (type) {
            case TEMPERATURE -> MeasurementType.TEMPERATURE;
            case HUMIDITY -> MeasurementType.HUMIDITY;
            case WATER -> MeasurementType.WATER_LEVEL;
            case LIGHT -> MeasurementType.LIGHT_INTENSITY;
            case MOISTURE -> MeasurementType.SOLID;
        };
    }

    private MeasurementUnit mapSensorToUnit(SensorType type) {
        return switch (type) {
            case TEMPERATURE -> MeasurementUnit.CELSIUS;
            case HUMIDITY, MOISTURE -> MeasurementUnit.PERCENT;
            case WATER -> MeasurementUnit.LITERS;
            case LIGHT -> MeasurementUnit.LUX;
        };
    }
}
