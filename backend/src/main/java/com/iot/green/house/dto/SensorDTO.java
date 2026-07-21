package com.iot.green.house.dto;

import com.iot.green.house.enums.SensorType;
import com.iot.green.house.model.Measurement;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SensorDTO {
    private Long id;
    private String name;
    private SensorType type;
    private String location;
    private String model;
    private boolean online;
    private LocalDateTime lastMeasurement;
    private List<Measurement> measurements;
}
