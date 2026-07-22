package com.iot.green.house.controller;

import com.iot.green.house.controller.rest.WeatherControllerApi;
import com.iot.green.house.dto.WeatherDTO;
import com.iot.green.house.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WeatherController implements WeatherControllerApi {

    private final WeatherService weatherService;
    @Override
    public ResponseEntity<WeatherDTO> getWeather(){
        return ResponseEntity.ok(
                weatherService.getWeather()
        );
    }

}