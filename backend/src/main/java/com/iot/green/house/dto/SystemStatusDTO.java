package com.iot.green.house.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemStatusDTO {
    private long totalSensors;
    private long onlineSensors;
    private long offlineSensors;
    private long activeAlerts;
    private boolean mqttOnline;
    private LocalDateTime lastUpdate;
}