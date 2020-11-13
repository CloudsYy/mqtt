package com.example.mqtt.mqttTest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @program: mqtt
 * @description:
 * @author: Cloud
 * @create: 2020/11/13 15:38:15
 */
public class ClientMqtt {

    private static final String Host = "";
    private static final String Top = "";
    private static final String clientId = "";
    private MqttClient client;
    private MqttConnectOptions options;
    private String username = "cloud";
    private String password = "cloud123";
    private ScheduledExecutorService scheduled;

    public void init(){
        try{
            // host为主机名，clientId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientId的保存形式，默认为以内存保存
            client = new MqttClient(Host,clientId,new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(username);
            // 设置连接的用户密码
            options.setPassword(password.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置断开后重新连接
            options.setAutomaticReconnect(true);
            // 设置回调
            client.setCallback(new PushCallBack());
            MqttTopic topic = client.getTopic(Top);
            // setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            // 遗嘱
            options.setWill(topic,"close".getBytes(),1,true);
            client.connect(options);
            // 订阅消息
            // 设置消息安全等级
            int[] qos = {1};
            String[] top1 = {Top};
            client.subscribe(top1,qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientMqtt clientMqtt = new ClientMqtt();
        clientMqtt.init();
    }

}
