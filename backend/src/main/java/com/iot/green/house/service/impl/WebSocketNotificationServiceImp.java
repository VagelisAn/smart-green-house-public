package com.iot.green.house.service.impl;

import com.iot.green.house.dto.SensorNotification;
import com.iot.green.house.service.WebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketNotificationServiceImp implements WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendSensorAlert(
            SensorNotification sensorNotification
    ) {
        log.info("[SEND NOTIFICATION] Data send: {} for {}", sensorNotification.getSensor(), sensorNotification.getValue());
        messagingTemplate.convertAndSend(
                "/topic/sensors",
                sensorNotification
        );
    }
    }
