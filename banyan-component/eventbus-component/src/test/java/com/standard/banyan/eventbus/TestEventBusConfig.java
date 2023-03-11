package com.standard.banyan.eventbus;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@TestConfiguration
public class TestEventBusConfig {

    @Bean
    public ThreadPoolExecutor eventBusExecutor() {
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(50);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("event-bus-thread-%d").build();
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        return new ThreadPoolExecutor(
                5, 20, 30, TimeUnit.SECONDS,
                workQueue, threadFactory, abortPolicy);
    }
}
