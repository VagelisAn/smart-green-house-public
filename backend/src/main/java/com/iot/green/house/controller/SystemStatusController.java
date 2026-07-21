package com.iot.green.house.controller;

import com.iot.green.house.controller.rest.SystemStatusControllerApi;
import com.iot.green.house.dto.SystemStatusDTO;
import com.iot.green.house.service.SystemStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemStatusController implements SystemStatusControllerApi {

    private final SystemStatusService systemStatusService;
    @Override
    public ResponseEntity<SystemStatusDTO> getStatus(){
        return ResponseEntity.ok(
                systemStatusService.getStatus()
        );
    }

}
