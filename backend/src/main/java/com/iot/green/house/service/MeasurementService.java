package com.iot.green.house.service;

import com.iot.green.house.model.Measurement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface MeasurementService {

    List<Measurement> getAll();
    Measurement getById(Long id);
    List<Measurement> getBySensorId(Long sensorId);
    Measurement getLastBySensor(Long sensorId);
    Optional<LocalDateTime> getLastMeasurementTime(Long sensorId);
    List<Measurement> getByDateRange(LocalDateTime from, LocalDateTime to);
    void delete(Long id);
    void saveMeasurement(long deviceId, Double value);
}
