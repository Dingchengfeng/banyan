package com.standard.banyan.driver.vda5050.mqtt;

import com.standard.banyan.driver.vda5050.mqtt.topic.PublishTopic;
import com.standard.banyan.driver.vda5050.mqtt.topic.Topic;
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
    private final MqttClientPersistence mqttClientPersistence;

    private final String brokerUrl;
    private final String clientId;
    private final String username;
    private final String password;

    public DefaultMqttClient(String brokerUrl, String clientId, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.mqttClientPersistence = new MemoryPersistence();
        try {
            mqttClient  = new MqttClient(this.brokerUrl,this.clientId,this.mqttClientPersistence);
        } catch (MqttException e) {
            log.error("mqttClient 创建失败:",e);
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
            log.error("mqttClient连接失败: username={},password={}", username, password,e);
        }
    }


    public void reconnect(){
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
           log.error("mqttClient重连失败: ",e);
        }
    }

    public void publish(PublishTopic topic, String msg){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(topic.getQos().getQosValue());
        mqttMessage.setRetained(topic.isRetained());

        try {
            mqttClient.publish(topic.getTopicFilter(),mqttMessage);
        } catch (MqttException e) {
            log.error("mqttClient发布消息失败:topic={},msg={}",topic, msg,e);
        }
    }

    public void subscribe(String topicFiler, Topic.Qos qos){
        try {
            mqttClient.subscribe(topicFiler,qos.getQosValue());
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
            log.error("mqttClient断开连接失败: ", e);
        }
    }

}
