package com.standard.banyan.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author dingchengfeng
 */
@Configuration
public class EventBusConfig {

    @Resource
    private ThreadPoolExecutor eventBusExecutor;

    @Bean
    public AsyncEventBus createAsyncEventBus() {
        return new AsyncEventBus(eventBusExecutor);
    }
}
