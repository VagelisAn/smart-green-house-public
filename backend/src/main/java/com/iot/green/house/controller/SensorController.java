package com.iot.green.house.controller;


import com.iot.green.house.controller.rest.SensorControllerApi;
import com.iot.green.house.dto.SensorDTO;
import com.iot.green.house.model.Sensor;
import com.iot.green.house.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SensorController implements SensorControllerApi {


    private final SensorService sensorService;

    @Override
    public List<Sensor> getAllSensors() {
        return sensorService.getAllSensors();
    }

    @Override
    public ResponseEntity<List<SensorDTO>> getAllSensorsWithTimeStamp() {
        return ResponseEntity.ok(
                sensorService.getAllSensorsWithTimeStamp()
        );
    }

    @Override
    public ResponseEntity<Sensor> getSensorById(Long id) {
        return ResponseEntity.ok(
                sensorService.getSensorById(id)
        );
    }

    @Override
    public ResponseEntity<Sensor> createSensor(
            Sensor sensor
    ) {
        return ResponseEntity.ok(
                sensorService.createSensor(sensor)
        );
    }

    @Override
    public ResponseEntity<Sensor> updateSensor(
            Long id,
            Sensor sensor
    ) {
        return ResponseEntity.ok(
                sensorService.updateSensor(id, sensor)
        );
    }

    @Override
    public ResponseEntity<Void> deleteSensor(Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}