/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.order;

import com.standard.banyan.driver.vda5050.adapter.message.common.Action;
import com.standard.banyan.driver.vda5050.adapter.message.common.NodePosition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * A node to be traversed for fulfilling an order.
 */
@Getter
@Setter
public class Node implements Serializable {

  /**
   * Unique node identification.
   */
  private String nodeId;
  /**
   * Number to track the sequence of nodes and edges in an order and to simplify order updates.
   * <p>
   * The main purpose is to distinguish between a node which is passed more than once within one
   * {@code orderId}. The variable {@code sequenceId} runs across all nodes and edges of the same
   * order and is reset when a new {@code orderId} is issued.
   */
  private Long sequenceId;
  /**
   * [Optional] Additional information on the node.
   */
  private String nodeDescription;
  /**
   * Whether the node is released or not.
   * <p>
   * Interpretation of values:
   * <ul>
   * <li>{@code true} (released) indicates that the node is part of the base.</li>
   * <li>{@code false} (planned) indicates that the node is part of the horizon.</li>
   * </ul>
   */
  private Boolean released;
  /**
   * [Optional] Defines the position on a map in world coordinates.
   * <p>
   * Optional for vehicle types that do not require a node position (e.g. line-guided vehicles).
   */
  private NodePosition nodePosition;

  private List<Action> actions;


}
