package com.standard.banyan.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author dingchengfeng
 * @date 2023/2/13
 **/
@Slf4j
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = { "com.standard.banyan" })
public class DemoApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApp.class);
        app.setApplicationStartup(new BufferingApplicationStartup(2048));
        app.run(args);
    }
}
