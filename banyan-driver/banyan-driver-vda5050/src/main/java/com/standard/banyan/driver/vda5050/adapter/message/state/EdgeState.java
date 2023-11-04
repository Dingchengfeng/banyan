package com.standard.banyan.driver.vda5050.adapter.message.state;


import com.standard.banyan.driver.vda5050.adapter.message.common.Trajectory;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 未走完的edge
 * @author dingchengfeng
 */
@Getter
@Setter
public class EdgeState implements Serializable {

  /**
   * id
   */
  private String edgeId;
  /**
   * Sequence id.
   */
  private Long sequenceId;
  /**
   * [可选] 描述
   */
  private String edgeDescription;
  /**
   * 是否有路权
   */
  private Boolean released;
  /**
   * [可选] 轨迹
   */
  private Trajectory trajectory;

}
