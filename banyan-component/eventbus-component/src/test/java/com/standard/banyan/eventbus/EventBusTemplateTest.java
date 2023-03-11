package com.standard.banyan.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(value = { EventBusTemplate.class, EventBusConfig.class, EventListenerTest.class, TestEventBusConfig.class})
class EventBusTemplateTest {

    @Autowired
    private EventBusTemplate eventBusTemplate;

    @Test
    void postEvent() {
        log.info("------post event-----");
        eventBusTemplate.postEvent(new TestEvent("test", "test"));
    }
}