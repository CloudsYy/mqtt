package com.example.mqtt.mqttTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: mqtt
 * @description:
 * @author: Cloud
 * @create: 2020/11/13 15:17:19
 */
@Component
public class MqttConfig {

    @Value("{emq.broker}")
    private String broker;

    @Value("{emq.qos}")
    private int[] qos;

    @Value("{emq.userName}")
    private String userName;

    @Value("{emq.password}")
    private String password;

    @Value("{emq.topic}")
    private String topic;

    @Value("{emq.pos}")
    private String pos;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public int[] getQos() {
        return qos;
    }

    public void setQos(int[] qos) {
        this.qos = qos;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
