package com.iot.green.house.client;

import com.iot.green.house.dto.OpenMeteoResponse;
import com.iot.green.house.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    private final RestTemplate restTemplate;

    private static final String URL =
            "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=40.5147" +
                    "&longitude=21.6786" +
                    "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

    public WeatherDTO getWeather() {
        OpenMeteoResponse response =
                restTemplate.getForObject(
                        URL,
                        OpenMeteoResponse.class
                );
        return WeatherDTO.builder()
                .temperature(
                        response.getCurrent()
                                .getTemperature_2m()
                )
                .humidity(
                        response.getCurrent()
                                .getRelative_humidity_2m()
                )
                .windSpeed(
                        response.getCurrent()
                                .getWind_speed_10m()
                )
                .weatherDescription(
                        "LIVE"
                )
                .location("Ptolemaida")
                .build();
    }
}