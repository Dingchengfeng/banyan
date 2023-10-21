package com.standard.banyan.driver.vda5050.adapter;

import com.standard.banyan.driver.amr.CommAdapter;
import com.standard.banyan.driver.amr.command.Command;
import com.standard.banyan.driver.amr.domain.AmrInfo;
import com.standard.banyan.driver.amr.domain.Movement;
import com.standard.banyan.driver.vda5050.mqtt.DefaultMqttClient;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@AllArgsConstructor
public class Vda5050CommAdapter implements CommAdapter {

    private AmrInfo amrInfo;

    private DefaultMqttClient mqttClient;

    @Override
    public void execute(Command command) {

    }

    @Override
    public void append(Movement order) {

    }

    @Override
    public List<Command> getCommandList() {
        return null;
    }

    @Override
    public AmrInfo getAmrInfo() {
        return amrInfo;
    }
}
