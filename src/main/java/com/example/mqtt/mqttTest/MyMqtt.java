package com.example.mqtt.mqttTest;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @program: mqtt
 * @description:
 * @author: Cloud
 * @create: 2020/11/13 13:42:52
 */
public class MyMqtt {

    private String host = "tcp://localhost:61613";
    private String userName = "admin";
    private String password = "password";
    private MqttClient client;
    private String id;
    private static MyMqtt instance;
    private MqttTopic mqttTopic;
    private String myTopic = "Topics/htjs/ServerToPhone";
    private MqttMessage mqttMessage;

    public MyMqtt(String id){
        this(id, null, false);
    }

    // 初始化mqtt
    public MyMqtt(String id, MqttCallback callback, boolean cleanSession) {
        try {
            // id应该保持唯一性
            client = new MqttClient(host,id,new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(cleanSession);
            options.setUserName(userName);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            if (callback != null) {
                client.setCallback(
                        new MqttCallback() {
                            @Override
                            public void connectionLost(Throwable cause) {
                                System.out.println(id + " cause = " + cause);
                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {
                                System.out.println(id + " topic = " + topic);
                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {
                                System.out.println(id+ " token = " + token);
                            }
                        }
                );
            } else {
                client.setCallback(callback);
            }
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        sendMessage(myTopic, msg);
    }

    // 发送消息
    public void sendMessage(String myTopic, String msg){
        try {
            mqttMessage = new MqttMessage();
            mqttMessage.setQos(1);
            mqttMessage.setRetained(false);
            mqttMessage.setPayload(msg.getBytes());
            mqttTopic = client.getTopic(myTopic);
            // 发布主题
            MqttDeliveryToken publish = mqttTopic.publish(mqttMessage);
            publish.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String[] topicFilters, int[] qs){
        try {
            // 订阅主题
            client.subscribe(topicFilters,qs);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
