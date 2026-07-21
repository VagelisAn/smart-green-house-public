package com.iot.green.house.controller.rest;

import com.iot.green.house.dto.SensorDTO;
import com.iot.green.house.model.Sensor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/sensors")
public interface SensorControllerApi {

    // GET ALL
    @GetMapping
    List<Sensor> getAllSensors();

    @GetMapping("/status")
    ResponseEntity<List<SensorDTO>> getAllSensorsWithTimeStamp( );

    @GetMapping("/{id}")
    ResponseEntity<Sensor> getSensorById(
            @PathVariable Long id
    );

    @PostMapping
    ResponseEntity<Sensor> createSensor(
            @RequestBody Sensor sensor
    );

    @PutMapping("/{id}")
    ResponseEntity<Sensor> updateSensor(
            @PathVariable Long id,
            @RequestBody Sensor sensor
    );

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSensor(
            @PathVariable Long id
    );

}