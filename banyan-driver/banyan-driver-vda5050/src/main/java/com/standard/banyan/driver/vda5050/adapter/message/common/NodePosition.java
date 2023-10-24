/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Defines the position on a map in world coordinates.
 * <p>
 * Each floor has its own map. All maps must use the same project specific global origin.
 */
@Getter
@Setter
public class NodePosition implements Serializable {

  /**
   * X-position on the map in reference to the map coordinate system (in m).
   * Precision is up to the specific implementation.
   */
  private Double x;
  /**
   * Y-position on the map in reference to the map coordinate system (in m).
   * Precision is up to the specific implementation.
   */
  private Double y;
  /**
   * [Optional] Orientation of the AGV on the node (in rad). Range: [-PI ... PI]
   * <p>
   * Optional for AGVs that plan their path by themselves.
   * <p>
   * If defined, the AGV has to assume the theta angle on this node. If previous edge disallows
   * rotation, the AGV is to rotate on the node. If following edge has a differing orientation
   * defined but disallows rotation, the AGV is to rotate on the node to the edges desired rotation
   * before entering the edge.
   */
  private Double theta;
  /**
   * [Optional] Indicates how exact an AGV has to drive over a node in order for it to count as
   * traversed.
   * <p>
   * Interpretation of values:
   * <ul>
   * <li>If equals 0: No deviation is allowed. (No deviation means within the normal tolerance of
   * the AGV manufacturer.)</li>
   * <li>If greater than 0: Allowed deviation radius (in m). If the AGV passes a node within the
   * deviation radius, the node is considered to have been traversed.</li>
   * </ul>
   */
  private Double allowedDeviationXY;
  /**
   * [Optional] Indicates how big the deviation of theta angle can be. Range: [0 ... PI]
   * <p>
   * The lowest acceptable angle is {@code <theta> - <allowedDevaitionTheta>} and the highest
   * acceptable angle is {@code <theta> + <allowedDeviationTheta>}.
   */
  private Double allowedDeviationTheta;
  /**
   * Unique identification of the map in which the position is referenced.
   * <p>
   * Each map has the same origin of coordinates. When an AGV uses an elevator, e. g. leading from a
   * departure floor to a target floor, it will disappear off the map of the departure floor and
   * spawn in the related lift node on the map of the target floor.
   */
  private String mapId;
  /**
   * [Optional] Additional information on the map.
   */
  private String mapDescription;


}
