package com.standard.banyan.driver.vda5050.adapter;

import com.standard.banyan.driver.amr.CommAdapter;
import com.standard.banyan.driver.amr.CommAdapterFactory;
import com.standard.banyan.driver.amr.MessageHandler;
import com.standard.banyan.driver.amr.domain.AmrInfo;
import com.standard.banyan.driver.amr.domain.DriverConfig;
import com.standard.banyan.driver.vda5050.mqtt.DefaultMqttClient;
import com.standard.banyan.driver.vda5050.mqtt.MessageCallback;

import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public class Vda5050CommAdapterFactory implements CommAdapterFactory {
    private DefaultMqttClient defaultMqttClient;
    private static final String ADAPTER_KEY = "amr_vda5050_2.0.0";
    @Override
    public void init(DriverConfig driverConfig, MessageHandler messageHandler) {
        this.defaultMqttClient = new DefaultMqttClient(driverConfig.getBrokerUrl(),driverConfig.getClientId(),driverConfig.getUsername(),driverConfig.getPassword());
        defaultMqttClient.connect(new MessageCallback(defaultMqttClient));
    }

    @Override
    public List<String> getAdapterKeys() {
        return List.of(ADAPTER_KEY);
    }

    @Override
    public boolean canProvideAdapter(String adapterKey) {
        return getAdapterKeys().contains(adapterKey);
    }

    @Override
    public CommAdapter createAdapter(AmrInfo amrInfo) {
        return new Vda5050CommAdapter(amrInfo,defaultMqttClient);
    }

    @Override
    public void destroy() {
        defaultMqttClient.disconnect();
    }
}
