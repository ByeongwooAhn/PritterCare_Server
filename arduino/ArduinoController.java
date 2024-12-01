package com.example.demo.arduino;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arduino")
public class ArduinoController {

    @GetMapping("/send-value")
    public ResponseEntity<String> receiveValue(
            @RequestParam("temp") String temp,
            @RequestParam("humidity") String humidity,
            @RequestParam("waterLevel") String waterLevel) {

        System.out.printf("Received temp: %s\nReceived humidity: %s\nReceived waterLevel: %s\n", temp, humidity, waterLevel);
        return ResponseEntity.ok(String.format("Received temp: %s\nReceived humidity: %s\nReceived waterLevel: %s\n", temp, humidity, waterLevel));
    }
    
    @PostMapping("/send-value")
    public ResponseEntity<String> receiveValue(@RequestBody SensorData sensorData) {

        System.out.printf("Received temp: %s\nReceived humidity: %s\nReceived waterLevel: %s\n", 
                sensorData.getTemp(), sensorData.getHumidity(), sensorData.getWaterLevel());

        return ResponseEntity.ok(String.format("Received temp: %s\nReceived humidity: %s\nReceived waterLevel: %s\n",
                sensorData.getTemp(), sensorData.getHumidity(), sensorData.getWaterLevel()));
    }
}