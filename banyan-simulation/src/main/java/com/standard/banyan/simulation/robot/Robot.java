package com.standard.banyan.simulation.robot;

import com.standard.banyan.simulation.mqtt.DefaultMqttClient;

/**
 * @author dingchengfeng
 * @date 2023/11/08
 */
public class Robot {
    private String manufacturer;

    private String serialNumber;

    private DefaultMqttClient defaultMqttClient;

    private String topicFormat =  "uagv/v2/%s/%s/%s";









}
