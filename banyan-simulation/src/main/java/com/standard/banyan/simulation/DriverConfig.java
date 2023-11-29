package com.standard.banyan.simulation;

import lombok.Data;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/11/29
 */
@Data
public class DriverConfig {
    private Mqtt mqtt;
    @Data
    public static class Mqtt{

        private final String brokerUrl;

        private final String clientId;

        private final String username;

        private final String password;
    }
}
