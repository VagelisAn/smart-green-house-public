package com.iot.green.house.service.impl;

import com.iot.green.house.dto.SensorDTO;
import com.iot.green.house.model.Sensor;
import com.iot.green.house.repository.MeasurementRepository;
import com.iot.green.house.repository.SensorRepository;
import com.iot.green.house.service.MeasurementService;
import com.iot.green.house.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImp implements SensorService {

    private final SensorRepository sensorRepository;
    private final MeasurementService measurementService;

    @Value("${sensor.online.timeout.minutes}")
    private int timeout;
    // 📌 GET ALL
    @Override
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public List<SensorDTO> getAllSensorsWithTimeStamp() {
        return sensorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // 📌 GET BY ID
    @Override
    public Sensor getSensorById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor not found: " + id));
    }

    // 📌 CREATE
    @Override
    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    // 📌 UPDATE
    @Override
    public Sensor updateSensor(Long id, Sensor updatedSensor) {

        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor not found: " + id));

        sensor.setName(updatedSensor.getName());
        sensor.setLocation(updatedSensor.getLocation());
        sensor.setModel(updatedSensor.getModel());
        sensor.setType(updatedSensor.getType());

        return sensorRepository.save(sensor);
    }

    // 📌 DELETE
    @Override
    public void deleteSensor(Long id) {

        if (!sensorRepository.existsById(id)) {
            throw new RuntimeException("Sensor not found: " + id);
        }

        sensorRepository.deleteById(id);
    }

    private SensorDTO mapToDto(Sensor sensor){

       boolean online = false;

        Optional<LocalDateTime> last =
                measurementService.getLastMeasurementTime(sensor.getId());

        LocalDateTime time = null;
        if(last.isPresent()){
            time = last.get();

            online =
                    Duration.between(time, LocalDateTime.now())
                            .toMinutes() < timeout;

            log.info("Last measurement : {}", time);
            log.info("Threshold        : {}", LocalDateTime.now().minusMinutes(timeout));
            log.info("Online           : {}", online);
        }
        return SensorDTO.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .type(sensor.getType())
                .model(sensor.getModel())
                .location(sensor.getLocation())
                .lastMeasurement(time)
                .online(online).build();
    }
}
