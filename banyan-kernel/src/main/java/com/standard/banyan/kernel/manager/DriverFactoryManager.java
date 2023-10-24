package com.standard.banyan.kernel.manager;

import com.standard.banyan.driver.amr.CommAdapterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 驱动管理器
 *
 * @author dingchengfeng
 * @date 2023/10/24
 */
@Component
@Slf4j
public class DriverFactoryManager {
    private final Set<CommAdapterFactory> commAdapterFactories = new HashSet<>();


}
