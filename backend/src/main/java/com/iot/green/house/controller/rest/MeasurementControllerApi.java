package com.iot.green.house.controller.rest;

import com.iot.green.house.model.Measurement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/api/measurements")
public interface MeasurementControllerApi {


    @GetMapping
    List<Measurement> getAll();


    @GetMapping("/{id}")
    ResponseEntity<Measurement> getById(
            @PathVariable Long id
    );


    @GetMapping("/sensor/{sensorId}")
    List<Measurement> getBySensor(
            @PathVariable Long sensorId
    );


    @GetMapping("/sensor/{sensorId}/last")
    Measurement getLast(
            @PathVariable Long sensorId
    );


    @GetMapping("/range")
    List<Measurement> getByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    );


    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    );

}