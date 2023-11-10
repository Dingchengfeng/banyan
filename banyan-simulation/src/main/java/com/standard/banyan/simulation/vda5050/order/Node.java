package com.standard.banyan.simulation.vda5050.order;


import com.standard.banyan.simulation.vda5050.common.Action;
import com.standard.banyan.simulation.vda5050.common.NodePosition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 节点
 * @author dingchengfeng
 */
@Getter
@Setter
public class Node implements Serializable {

  /**
   * 节点id
   */
  private String nodeId;
  /**
   * 每个order从0开始，随node和edge递增
   */
  private Long sequenceId;
  /**
   * [Optional] 描述
   */
  private String nodeDescription;
  /**
   * 是否有路权
   */
  private Boolean released;
  /**
   * 节点位置
   */
  private NodePosition nodePosition;

  /**
   * 动作
   */
  private List<Action> actions;
}
