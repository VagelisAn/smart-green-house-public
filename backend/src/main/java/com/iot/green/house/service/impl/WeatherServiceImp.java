package com.iot.green.house.service.impl;

import com.iot.green.house.client.WeatherApiClient;
import com.iot.green.house.dto.WeatherDTO;
import com.iot.green.house.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImp implements WeatherService {
    private final WeatherApiClient client;
    public WeatherDTO getWeather(){
        return client.getWeather();
    }
}
