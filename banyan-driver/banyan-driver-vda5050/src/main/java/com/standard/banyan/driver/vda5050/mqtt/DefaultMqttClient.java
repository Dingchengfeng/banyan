package com.standard.banyan.driver.vda5050.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Slf4j
public class DefaultMqttClient {
    private IMqttClient mqttClient;
    private MqttClientPersistence mqttClientPersistence;

    private String brokerUrl;
    private String clientId;
    private String username;
    private String password;

    public DefaultMqttClient(String brokerUrl, String clientId, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.mqttClientPersistence = new MemoryPersistence();
        try {
            mqttClient  = new MqttClient(this.brokerUrl,this.clientId,this.mqttClientPersistence);
        } catch (MqttException e) {
            log.error("mqttClient创建失败:username={},password={},",username, password,e);
        }

    }

    public void connect(MqttCallback mqttCallback) {
        MqttConnectionOptions connOpts = new MqttConnectionOptions();
        connOpts.setUserName(this.username);
        connOpts.setPassword(this.password.getBytes());
        connOpts.setCleanStart(true);
        connOpts.setAutomaticReconnect(true);
        mqttClient.setCallback(mqttCallback);
        try {
            mqttClient.connect(connOpts);
        } catch (MqttException e) {
            log.error("mqttClient连接失败: errorMsg={},username={},password={}", e.getMessage(), username, password);
        }
    }


    public void reconnect(){
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
           log.error("mqttClient重连失败: errorMsg={}",e.getMessage());
        }
    }

    public void publish(String topic, byte[] msg, QosEnum qos, boolean retained){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg);
        mqttMessage.setQos(qos.ordinal());
        mqttMessage.setRetained(retained);

        try {
            mqttClient.publish(topic,mqttMessage);
        } catch (MqttException e) {
            log.error("mqttClient发布消息失败: errorMsg={},topic={},msg={},qos={},retained={}", e.getMessage(),topic, new  String(msg,StandardCharsets.UTF_8),qos,retained);
        }
    }

    public void subscribe(String topicFiler, QosEnum qos){
        try {
            mqttClient.subscribe(topicFiler,qos.ordinal());
        } catch (MqttException e) {
            log.error("mqttClient订阅消息失败: errorMsg={},topicFiler={},qos={}",e.getMessage(),topicFiler,qos);
        }
    }

    public void subscribe(String[] topicFiler,int[] qos){
        try {
            mqttClient.subscribe(topicFiler,qos);
        } catch (MqttException e) {
            log.error("mqttClient订阅消息失败: errorMsg={},topicFiler={},qos={}", e.getMessage(),topicFiler,qos);
        }
    }

    public void disconnect(){
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("mqttClient断开失败: ", e);
        }
    }

}
