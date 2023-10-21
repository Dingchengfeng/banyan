package com.standard.banyan.driver.vda5050.mqtt;

/**
 * mqtt主题过滤器
 * @author dingchengfeng
 * @date 2021/11/8
 **/
public class MqttTopicFilter {
    private MqttTopicFilter(){
    }
    /**
     * 分组共享订阅,主要用于在一组订阅者之间做负载均衡
     */
    public static final String PREFIX_SHARE_LAND = "$share/rcs/";
    /**
     * 不分组共享订阅,主要用于在所有订阅者之间做负载均衡
     */
    public static final String PREFIX_QUEUE = "$queue/";

    public static final String ORDER = "uagv/v2.0.0/%s/%s/order";
    public static final String INSTANT_ACTIONS = "uagv/v2.0.0/%s/%s/instantActions";

    public static final String CONNECTION = "uagv/v2.0.0/+/+/connection";
    public static final String FACTSHEET = "uagv/v2.0.0/+/+/factsheet";
    public static final String STATE = "uagv/v2.0.0/+/+/state";
    public static final String VISUALIZATION = "uagv/v2.0.0/+/+/visualization";


}
