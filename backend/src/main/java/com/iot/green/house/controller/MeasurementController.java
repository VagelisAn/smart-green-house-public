package com.iot.green.house.controller;

import com.iot.green.house.model.Measurement;
import com.iot.green.house.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import com.iot.green.house.controller.rest.MeasurementControllerApi;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MeasurementController implements MeasurementControllerApi {

    private final MeasurementService measurementService;

    @Override
    public List<Measurement> getAll() {
        return measurementService.getAll();
    }

    @Override
    public ResponseEntity<Measurement> getById(Long id) {
        return ResponseEntity.ok(
                measurementService.getById(id)
        );
    }

    @Override
    public List<Measurement> getBySensor(Long sensorId) {
        return measurementService.getBySensorId(sensorId);
    }

    @Override
    public Measurement getLast(Long sensorId) {
        return measurementService.getLastBySensor(sensorId);
    }

    @Override
    public List<Measurement> getByDateRange(
            LocalDateTime from,
            LocalDateTime to
    ) {
        return measurementService.getByDateRange(from,to);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        measurementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
