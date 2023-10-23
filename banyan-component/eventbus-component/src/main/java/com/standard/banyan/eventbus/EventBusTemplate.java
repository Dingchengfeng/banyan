package com.standard.banyan.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingchengfeng
 */
@Slf4j
@Component
public class EventBusTemplate implements InitializingBean, ApplicationContextAware {

    private Map<String, EventListener> map = new HashMap<>();

    @Autowired
    private AsyncEventBus asyncEventBus;

    public void postEvent(Object event) {
        try {
            asyncEventBus.post(event);
        } catch (Exception e) {
            log.error("在高并发的环境下使用AsyncEventBus时，发送事件可能会出现异常，" +
                    "因为它使用的线程池，当线程池的线程不够用时，会拒绝接收任务，就会执行线程池的拒绝策略，" +
                    "如果需要关注是否提交事件成功，" +
                    "就需要将线程池的拒绝策略设为抛出异常，并且try-catch来捕获异常", e);
            // TODO 落表或其它处理
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (EventListener listener : map.values()) {
            asyncEventBus.register(listener);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        map = applicationContext.getBeansOfType(EventListener.class);
    }
}
