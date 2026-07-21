package com.iot.green.house.service;

import com.iot.green.house.dto.SensorNotification;



public interface WebSocketNotificationService {
    void sendSensorAlert( SensorNotification sensorNotification );
}
