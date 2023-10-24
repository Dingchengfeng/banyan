/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.common;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ControlPoint implements Serializable {

  /**
   * X coordinate described in the world coordinate system (in m).
   */
  private Double x;
  /**
   * Y coordinate described in the world coordinate system (in m).
   */
  private Double y;
  /**
   * The weight with which this control point pulls on the curve. Range: [0 ... infinity]
   * <p>
   * When not defined, the default will be 1.0.
   */
  private Double weight;
  /**
   * [Optional] Orientation of the AGV on this position of the curve. Range: [-PI ... PI]
   * <p>
   * The orientation is in world coordinates. When not defined the orientation of the AGV will be
   * tangential to the curve.
   */
  private Double orientation;
}
