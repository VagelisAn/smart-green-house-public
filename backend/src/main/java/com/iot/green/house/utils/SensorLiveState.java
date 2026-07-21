package com.iot.green.house.utils;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class SensorLiveState {
    // Κλειδί: device_uid (π.χ. "arduino_sensor_5"), Τιμή: η τελευταία μέτρηση
    private final Map<Long, Double> liveData = new ConcurrentHashMap<>();

    public void updateValue(long deviceId, Double value) {
        liveData.put(deviceId, value);
    }

    public Double getValue(long deviceUid) {
        return liveData.get(deviceUid);
    }
}
