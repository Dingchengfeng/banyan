/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.order;


import com.standard.banyan.driver.vda5050.adapter.message.common.Action;
import com.standard.banyan.driver.vda5050.adapter.message.common.Trajectory;

import java.io.Serializable;
import java.util.List;


public class Edge implements Serializable {

  /**
   * Unique edge identification.
   */
  private String edgeId;
  /**
   * Number to track the sequence of nodes and edges in an order and to simplify order updates.
   * <p>
   * The variable {@code sequenceId} runs across all nodes and edges of the same order and is reset
   * when a new {@code orderId} is issued.
   */
  private Long sequenceId;
  /**
   * [Optional] Additional information on the edge.
   */
  private String edgeDescription;
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
   * The {@code nodeId} of the start node.
   */
  private String startNodeId;
  /**
   * The {@code nodeId} of the end node.
   */
  private String endNodeId;
  /**
   * [Optional] Permitted maximum speed on the edge(in m/s).
   * <p>
   * Speed is defined by the fastest measurement of the vehicle.
   */
  private Double maxSpeed;
  /**
   * [Optional] Permitted maximum height of the vehicle on the edge, including the load (in m).
   */
  private Double maxHeight;
  /**
   * [Optional] Permitted minimal height of the load handling device on the edge (in m).
   */
  private Double minHeight;
  /**
   * [Optional] Orientation of the AGV on the edge.
   * <p>
   * The value {@code orientationType} defines if it has to be interpreted relative to the global
   * project specific map coordinate system or tangential to the edge. In case of interpreted
   * tangential to the edge 0.0 = forwards and PI = backwards.
   * <p>
   * If AGV starts in different orientation, rotate the vehicle on the edge to the desired
   * orientation if {@code rotationAllowed} is set to {@code true}. If {@code rotationAllowed} is
   * {@code false}, rotate before entering the edge. If that is not possible, reject the order.
   * <p>
   * If no trajectory is defined, apply the rotation to the direct path between the two connecting
   * nodes of the edge. If a trajectory is defined for the edge, apply the orientation to the
   * trajectory.
   */
  private Double orientation;
  /**
   * [Optional] Sets direction at junctions for line-guided vehicles, to be defined initially
   * (vehicle-individual).
   * <p>
   * Example: left, right, straight.
   */
  private String direction;
  /**
   * [Optional] Whether rotation on the edge is allowed or not.
   */
  private Boolean rotationAllowed;
  /**
   * [Optional] Maximum rotation speed (in rad/s).
   */
  private Double maxRotationSpeed;
  /**
   * [Optional] Length of the path from {@code startNode} to {@code endNode} (in m).
   * <p>
   * This value is used by line-guided AGVs to decrease their speed before reaching a stop position.
   */
  private Double length;
  /**
   * [Optional] Trajectory object for this edge as NURBS.
   * <p>
   * Defines the curve on which the AGV should move between {@code startNode} and {@code endNode}.
   * Can be omitted if an AGV cannot process trajectories or if an AGV plans its own trajectory.
   */
  private Trajectory trajectory;

  private List<Action> actions;


}
