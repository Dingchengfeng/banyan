package com.standard.banyan.driver.amr.domain;

import lombok.Data;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Data
public class DriverConfig {
    private String brokerUrl = "localhost:1883";
    private String clientId = "driver";
    private String username = "admin";
    private String password = "admin";
}
