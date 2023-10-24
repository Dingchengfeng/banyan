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
 * Point of reference for the location of the bounding box.
 * <p>
 * The point of reference is always the center of the bounding box's bottom surface (at height = 0)
 * and is described in coordinates of the AGV's coordinate system.
 */
@Getter
@Setter
public class BoundingBoxReference  implements Serializable {

  /**
   * X-coordinate of the point of reference.
   */
  private Double x;
  /**
   * Y-coordinate of the point of reference.
   */
  private Double y;
  /**
   * Z-coordinate of the point of reference.
   */
  private Double z;
  /**
   * [Optional] Orientation of the loads bounding box.
   * <p>
   * Important e.g. for tugger trains.
   */
  private Double theta;

}
