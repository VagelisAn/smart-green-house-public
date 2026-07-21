package com.iot.green.house.controller.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface LiveControllerApi {
        @GetMapping("/realtime/{deviceUid}")
        ResponseEntity<Map<String, Object>> getRealtimeValue(@PathVariable Long deviceId);

}
