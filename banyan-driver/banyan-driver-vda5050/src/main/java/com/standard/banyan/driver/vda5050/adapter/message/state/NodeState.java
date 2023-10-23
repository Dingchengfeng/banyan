/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import com.standard.banyan.driver.vda5050.adapter.message.common.NodePosition;
import lombok.Data;

import java.io.Serializable;

/**
 * Information about a node the AGV still has to traverse.
 */
@Data
public class NodeState implements Serializable {

  /**
   * Unique node identification.
   */
  private String nodeId;
  /**
   * Sequence id to differentiate between multiple nodes with the same {@code nodeId}.
   */
  private Long sequenceId;
  /**
   * [Optional] Additional information on the node.
   */
  private String nodeDescription;
  /**
   * [Optional] The node position.
   * <p>
   * Can be sent additionally, e.g. for debugging purposes.
   */
  private NodePosition nodePosition;
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



}
