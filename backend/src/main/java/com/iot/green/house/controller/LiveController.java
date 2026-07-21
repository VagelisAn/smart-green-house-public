package com.iot.green.house.controller;


import com.iot.green.house.controller.rest.LiveControllerApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class LiveController implements LiveControllerApi {

//    private final SensorLiveState liveState;

    @Override
    public ResponseEntity<Map<String, Object>> getRealtimeValue(
            Long deviceUid
    ) {
//
//        Double value = liveState.getValue(deviceUid);
//
//        if (value == null) {
//            return ResponseEntity.notFound().build();
//        }
        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("deviceId", deviceUid);
//        response.put("value", value);
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

}