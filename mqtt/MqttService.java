package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.MqttConfig;

@Service
public class MqttService {

    private MqttClient client;

    // MqttConfig 객체 주입
    private final MqttConfig mqttConfig;

    // 생성자 주입을 통해 MqttConfig 객체 주입받기
    @Autowired
    public MqttService(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;

        try {
            // MqttClient 생성
            client = new MqttClient(mqttConfig.getBrokerUrl(), mqttConfig.getClientId());
            MqttConnectOptions options = new MqttConnectOptions();

            // 유저네임과 비밀번호 설정
            options.setUserName(mqttConfig.getUsername());
            options.setPassword(mqttConfig.getPassword().toCharArray());

            options.setCleanSession(true);  // 클린 세션 사용
            client.connect(options);  // MQTT 서버와 연결

            // 구독 시작
            subscribe();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // MQTT 메시지 게시 (Publish)
    public void publish(String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);  // 메시지 품질 수준 (1: 최소 한번 전달)
            client.publish(mqttConfig.getTopic(), mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // MQTT 구독 (Subscribe)
    private void subscribe() {
        try {
            // 메시지 수신 리스너 등록
            client.subscribe(mqttConfig.getTopic(), (topic, msg) -> {
                String message = new String(msg.getPayload());
            });
            System.out.println("Subscribed to topic: " + mqttConfig.getTopic());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 연결 종료 (Disconnect)
    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Disconnected from broker");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
