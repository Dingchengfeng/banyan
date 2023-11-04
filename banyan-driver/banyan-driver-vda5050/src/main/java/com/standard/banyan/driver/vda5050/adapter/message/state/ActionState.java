package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 动作状态
 * @author dingchengfeng
 */
@Getter
@Setter
public class ActionState implements Serializable {

  /**
   * id.
   */
  private String actionId;
  /**
   * 命令字
   */
  private String actionType;
  /**
   * [可选]描述
   */
  private String actionDescription;
  /**
   * 状态
   */
  private ActionStatus actionStatus;
  /**
   * [可选] 结果描述，列入RFID读取
   */
  private String resultDescription;

}
