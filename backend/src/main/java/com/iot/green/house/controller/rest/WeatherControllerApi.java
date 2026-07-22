package com.iot.green.house.controller.rest;

import com.iot.green.house.dto.WeatherDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/weather")
public interface WeatherControllerApi {
    @GetMapping
    ResponseEntity<WeatherDTO> getWeather();
}
