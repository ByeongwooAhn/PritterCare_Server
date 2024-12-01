package com.example.demo.mqtt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    private final MqttService mqttService;

    public MqttController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    // /publish 경로로 POST 요청이 들어오면 메시지를 게시
    @PostMapping("/publish")
    public String publishMessagePost(@RequestBody String message) {
        mqttService.publish(message);  // 받은 메시지를 MQTT로 게시
        System.out.println("web POST return: " + message);
        return "web POST return: " + message;
    }
    
    // /publish 경로로 GET 요청이 들어오면 메시지를 게시
    @GetMapping("/publish")
    public String publishMessageGet(
        @RequestParam("temp") String temp,
        @RequestParam("humidity") String humidity,
        @RequestParam("waterLevel") String waterLevel
    ) {
        // 받은 값들을 사용하여 메시지를 MQTT로 게시
        mqttService.publish("Temp: " + temp + ", Humidity: " + humidity + ", Water Level: " + waterLevel);
        
        System.out.println("web GET return: Temp: " + temp + ", Humidity: " + humidity + ", Water Level: " + waterLevel);
        
        // 반환값으로 받은 값들 출력
        return "web GET return: Temp: " + temp + ", Humidity: " + humidity + ", Water Level: " + waterLevel;
    }

}
