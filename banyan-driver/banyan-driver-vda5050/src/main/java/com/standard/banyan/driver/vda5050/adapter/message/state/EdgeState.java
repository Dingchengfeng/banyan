/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;


import com.standard.banyan.driver.vda5050.adapter.message.common.Trajectory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Information about an edge the AGV still has to traverse.
 */
@Getter
@Setter
public class EdgeState implements Serializable {

  /**
   * Unique edge identification.
   */
  private String edgeId;
  /**
   * Sequence id to differentiate between multiple edges with the same {@code edgeId}.
   */
  private Long sequenceId;
  /**
   * [Optional] Additional information on the edge.
   */
  private String edgeDescription;
  /**
   * Whether the edge is released or not.
   * <p>
   * Interpretation of values:
   * <ul>
   * <li>{@code true} (released) indicates that the edge is part of the base.</li>
   * <li>{@code false} (planned) indicates that the edge is part of the horizon.</li>
   * </ul>
   */
  private Boolean released;
  /**
   * [Optional] The trajectory is to be communicated as NURBS.
   * <p>
   * Trajectory segments are from the point where the AGV starts to enter the edge until the point
   * where it reports that the next node was traversed.
   */
  private Trajectory trajectory;

}
