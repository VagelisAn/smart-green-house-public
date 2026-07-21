package com.iot.green.house.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SensorNotification {

    private String type;
    private String sensor;
    private Double value;
    private String message;
}
