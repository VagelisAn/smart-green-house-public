package com.iot.green.house.service;

import com.iot.green.house.dto.SensorDTO;
import com.iot.green.house.model.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> getAllSensors();
    List<SensorDTO> getAllSensorsWithTimeStamp();
    Sensor getSensorById(Long id);
    Sensor createSensor(Sensor sensor);
    Sensor updateSensor(Long id, Sensor updatedSensor);
    void deleteSensor(Long id);
}
