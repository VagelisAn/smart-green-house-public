package com.iot.green.house.dto;

import lombok.Data;


@Data
public class OpenMeteoResponse {

    private Current current;

    @Data
    public static class Current {
        private double temperature_2m;
        private double relative_humidity_2m;
        private double wind_speed_10m;
    }
}
