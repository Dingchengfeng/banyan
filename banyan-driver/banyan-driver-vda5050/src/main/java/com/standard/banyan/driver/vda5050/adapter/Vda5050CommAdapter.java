package com.standard.banyan.driver.vda5050.adapter;

import com.standard.banyan.driver.amr.CommAdapter;
import com.standard.banyan.driver.amr.command.Command;
import com.standard.banyan.driver.amr.domain.AmrInfo;
import com.standard.banyan.driver.amr.domain.Movement;
import com.standard.banyan.driver.vda5050.mqtt.DefaultMqttClient;
import com.standard.banyan.driver.vda5050.mqtt.topic.PublishTopic;
import com.standard.banyan.driver.vda5050.mqtt.topic.Topic;
import com.standard.banyan.driver.vda5050.mqtt.topic.TopicFormat;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@AllArgsConstructor
public class Vda5050CommAdapter implements CommAdapter {

    private AmrInfo amrInfo;

    private DefaultMqttClient mqttClient;

    private PublishTopic orderTopic;

    private PublishTopic instantActionsTopic;

    public Vda5050CommAdapter(AmrInfo amrInfo, DefaultMqttClient mqttClient) {
        this.amrInfo = amrInfo;
        this.mqttClient = mqttClient;
        this.orderTopic = new PublishTopic(Topic.Qos.AT_MOST_ONCE,false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT,"v2",
                amrInfo.getManufacturer(),amrInfo.getSerialNumber(),"order"));
        this.instantActionsTopic = new PublishTopic(Topic.Qos.AT_MOST_ONCE,false,
            String.format(TopicFormat.VDA5050_TOPIC_FORMAT,"v2",
                amrInfo.getManufacturer(),amrInfo.getSerialNumber(),"instantActions"));
    }

    @Override
    public void execute(Command command) {
        mqttClient.publish(instantActionsTopic, "");
    }

    @Override
    public void append(Movement movement) {
        mqttClient.publish(orderTopic, "");
    }

    @Override
    public List<Command> getCommandList() {
        return new ArrayList<>();
    }

    @Override
    public AmrInfo getAmrInfo() {
        return amrInfo;
    }
}
