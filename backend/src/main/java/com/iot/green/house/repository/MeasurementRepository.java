package com.iot.green.house.repository;

import com.iot.green.house.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findBySensorId(Long sensorId);

    Optional<Measurement> findTopBySensorIdOrderByTimestampDesc(Long sensorId);

    List<Measurement> findByTimestampBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
       SELECT MAX(m.timestamp)
       FROM Measurement m
       WHERE m.sensor.id = :sensorId
       """)
    Optional<LocalDateTime> findLastMeasurementTime(Long sensorId);
}
