package com.standard.banyan.kernel.dispatch;

/**
 * @author dingchengfeng
 * @description 分配器
 * @date 2023/09/06
 */
public interface Dispatcher {
    /**
     * 启动任务分配器
     */
    void start();

    /**
     * 停止
     */
    void stop();
}
