package com.standard.banyan.driver.vda5050.mqtt.topic;

/**
 * 主题格式
 * @author dingchengfeng
 * @date 2023/10/23
 */
public class TopicFormat {
    private TopicFormat() {
    }

    private static final String INTERFACE_NAME = "uagv";
    private static final String MAJOR_VERSION = "%s";
    private static final String MANUFACTURER = "%s";
    private static final String SERIAL_NUMBER = "%s";
    private static final String TOPIC = "%s";

    public static final String VDA5050_TOPIC_FORMAT = String.join("/",
        INTERFACE_NAME,MAJOR_VERSION,MANUFACTURER,SERIAL_NUMBER,TOPIC);
}
