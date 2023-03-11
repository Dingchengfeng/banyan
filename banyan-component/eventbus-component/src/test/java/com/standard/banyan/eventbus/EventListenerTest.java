package com.standard.banyan.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestComponent;

@Slf4j
@TestComponent
class EventListenerTest implements EventListener {

    @Subscribe
    @AllowConcurrentEvents
    public void execute(TestEvent event) {
        log.info("收到消息 {}", event);
    }
}