package com.standard.banyan.simulation.service;

import com.standard.banyan.simulation.vda5050.Vda5050Robot;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/11/09
 */
@Component
public class RobotManager {
    private Map<String, Vda5050Robot>  robotMap = new HashMap<>(8);


}
