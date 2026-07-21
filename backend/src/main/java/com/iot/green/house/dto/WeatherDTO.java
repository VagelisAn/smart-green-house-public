package com.iot.green.house.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class WeatherDTO {
    private String location;
    private Double temperature;
    private Double feelsLike;
    private Double humidity;
    private Double windSpeed;
    private Integer weatherCode;
    private String weatherDescription;
    private LocalDateTime updatedAt;
    private Boolean live;
}
