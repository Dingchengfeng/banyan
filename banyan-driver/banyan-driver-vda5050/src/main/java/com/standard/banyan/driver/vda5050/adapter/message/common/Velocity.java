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
 * The AGV's velocity in vehicle coordinates.
 */
@Getter
@Setter
public class Velocity implements Serializable {

  /**
   * [Optional] The AGV's velocity in its x direction.
   */
  private Double vx;
  /**
   * [Optional] The AGV's velocity in its y direction.
   */
  private Double vy;
  /**
   * [Optional] The AGV's turning speed around its z axis.
   */
  private Double omega;

}
