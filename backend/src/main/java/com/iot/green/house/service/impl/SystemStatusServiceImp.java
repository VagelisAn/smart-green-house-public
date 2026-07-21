package com.iot.green.house.service.impl;

import com.iot.green.house.dto.SystemStatusDTO;
import com.iot.green.house.model.Measurement;
import com.iot.green.house.model.Sensor;
import com.iot.green.house.repository.MeasurementRepository;
import com.iot.green.house.repository.SensorRepository;
import com.iot.green.house.service.SystemStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemStatusServiceImp implements SystemStatusService {
    private final SensorRepository sensorRepository;

    private final MeasurementRepository measurementRepository;

    @Override
    public SystemStatusDTO getStatus(){
        SystemStatusDTO dto = new SystemStatusDTO();
        List<Sensor> sensors = sensorRepository.findAll();
        long total = sensors.size();
        long online = sensors.stream()
                .filter(sensor -> {

                    LocalDateTime last =
                            measurementRepository
                                    .findLastMeasurementTime(sensor.getId())
                                    .orElse(null);
                    if (last == null) {
                        return false;
                    }
                    return last.isAfter(
                            LocalDateTime.now().minusHours(1)
                    );
                })
                .count();
        dto.setTotalSensors(total);
        dto.setOnlineSensors(online);
        dto.setOfflineSensors(total - online);

        return dto;
    }
}
