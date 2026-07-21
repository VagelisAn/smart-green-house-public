package com.iot.green.house.controller.rest;

import com.iot.green.house.dto.SystemStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface SystemStatusControllerApi {
    @GetMapping("/status")
    ResponseEntity<SystemStatusDTO> getStatus();
}
