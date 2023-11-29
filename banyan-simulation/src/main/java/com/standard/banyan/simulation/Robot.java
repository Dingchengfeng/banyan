package com.standard.banyan.simulation;
/**
 * 抽象机器人
 * @author dingchengfeng
 * @date 2023/11/14
 */
public interface Robot extends Runnable {
    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();


}
