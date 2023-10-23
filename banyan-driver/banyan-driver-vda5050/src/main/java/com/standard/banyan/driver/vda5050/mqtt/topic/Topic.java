package com.standard.banyan.driver.vda5050.mqtt.topic;

/**
 * 主题
 * @author dingchengfeng
 * @date 2023/10/23
 */
public interface Topic {
    /**
     * 分组共享订阅,主要用于在一组订阅者之间做负载均衡,%s替换为具体分组
     */
    String PREFIX_SHARE_GROUP = "$share/%s/";
    /**
     * 不分组共享订阅,主要用于在所有订阅者之间做负载均衡
     */
    String PREFIX_QUEUE = "$queue/";

    /**
     * 主题过滤器
     * @return 主题过滤器
     */
    String getTopicFilter();

    /**
     * 消息质量
     * @return 消息质量
     */
    Qos getQos();

    enum Qos {
        /**
         * 最多传递一次消息（QoS值为0）
         */
        AT_MOST_ONCE(0),
        /**
         * 至少传递一次消息（QoS值为1）
         */
        AT_LEAST_ONCE(1),
        /**
         * 只传递一次消息（QoS值为2）
         */
        EXACTLY_ONCE(2);

        /**
         * MQTT指定的QoS值
         */
        private final int qosValue;

        /**
         * 创建新实例
         *
         * @param qosValue QoS值
         */
        Qos(int qosValue) {
            this.qosValue = qosValue;
        }

        /**
         * 返回MQTT指定的QoS值
         *
         * @return MQTT指定的QoS值
         */
        public int getQosValue() {
            return qosValue;
        }
    }
}
