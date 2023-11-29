package com.standard.banyan.simulation;

import com.standard.banyan.simulation.vda5050.Vda5050Robot;

/**
 * @author dingchengfeng
 * @date 2023/11/09
 */
public interface RobotFactory {
    /**
     * 创建机器人
     * @return 机器人
     */
    Vda5050Robot createRobot();

}
