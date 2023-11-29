package com.standard.banyan.simulation.vda5050.topic;
import lombok.Getter;

/**
 * @author dingchengfeng
 * @date 2023/11/14
 */
@Getter
public enum TopicType {
    ORDER("order"),
    INSTANT_ACTIONS("instantActions"),
    STATE("state"),
    VISUALIZATION("visualization"),
    CONNECTION("connection"),
    FACTSHEET("factsheet");

    TopicType(String name) {
        this.name = name;
    }

    private String name;
}
