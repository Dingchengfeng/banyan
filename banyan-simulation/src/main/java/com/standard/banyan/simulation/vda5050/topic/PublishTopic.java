package com.standard.banyan.simulation.vda5050.topic;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dingchengfeng
 * @date 2023/11/14
 */
@AllArgsConstructor
public class PublishTopic implements Topic {

    private final Qos qos;

    private final boolean retained;

    private final String topicFilter;

    private AtomicLong headerId;

    public long nextHeaderId() {
        return headerId.addAndGet(1L);
    }

    @Override
    public String getTopicFilter() {
        return topicFilter;
    }

    @Override
    public Qos getQos() {
        return qos;
    }

    @Override
    public boolean isRetained() {
        return retained;
    }
}
