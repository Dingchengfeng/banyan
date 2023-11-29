package com.standard.banyan.simulation.vda5050.entity.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 动作指令
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Getter
@Setter
public class Action implements Serializable {

  /**
   * 动作指令字
   */
  private String actionType;

  /**
   * id
   */
  private String actionId;

  /**
   *  [可选]描述
   */
  private String actionDescription;

  /**
   * 阻塞类型
   */
  private BlockingType blockingType;

  /**
   * [可选]参数
   */
  private List<ActionParameter> actionParameters;

  public enum BlockingType {

    /**
     * 可以和动作、移动并行
     */
    NONE,
    /**
     * 可以和动作并行，不得与移动并行
     */
    SOFT,
    /**
     * 不能并行
     */
    HARD;
  }

}
