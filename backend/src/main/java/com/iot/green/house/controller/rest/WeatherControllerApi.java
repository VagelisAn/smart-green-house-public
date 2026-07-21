package com.iot.green.house.controller.rest;

import com.iot.green.house.dto.WeatherDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface WeatherControllerApi {
    @GetMapping
    ResponseEntity<WeatherDTO> getWeather();
}
