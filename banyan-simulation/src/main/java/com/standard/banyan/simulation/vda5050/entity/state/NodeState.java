package com.standard.banyan.simulation.vda5050.entity.state;

import com.standard.banyan.simulation.vda5050.entity.common.NodePosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 未走完的点.
 * @author dingchengfeng
 */
@Getter
@Setter
@AllArgsConstructor
public class NodeState implements Serializable {

  /**
   * id
   */
  private String nodeId;
  /**
   * Sequence
   */
  private Long sequenceId;
  /**
   * [可选] 描述
   */
  private String nodeDescription;
  /**
   * [可选] 位置
   */
  private NodePosition nodePosition;
  /**
   * 是否有路权
   */
  private Boolean released;

}
