package com.standard.banyan.driver.vda5050.adapter.message.state;

/**
 * 动作状态
 * @author dingchengfeng
 */
public enum ActionStatus {

  /**
   * 等待.
   */
  WAITING,
  /**
   * 初始化.
   */
  INITIALIZING,
  /**
   * 运行中.
   */
  RUNNING,
  /**
   * 完成.
   */
  FINISHED,
  /**
   * 失败.
   */
  FAILED;
}
