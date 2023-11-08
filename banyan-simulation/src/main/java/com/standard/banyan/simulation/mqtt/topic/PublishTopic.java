package com.standard.banyan.simulation.mqtt.topic;


import lombok.AllArgsConstructor;

/**
 * 发布主题
 * @author dingchengfeng
 * @date 2023/10/23
 */
@AllArgsConstructor
public class PublishTopic implements Topic {
    private final Qos qos;
    private boolean retained;
    private String topicFilter;

    @Override
    public boolean isRetained() {
        return retained;
    }

    @Override
    public String getTopicFilter() {
        return topicFilter;
    }

    @Override
    public Qos getQos() {
        return qos;
    }
}
