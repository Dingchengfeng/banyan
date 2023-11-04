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
 * 速度
 */
@Getter
@Setter
public class Velocity implements Serializable {

  /**
   * [可选] x方向速度.
   */
  private Double vx;
  /**
   * [可选] y方向速度.
   */
  private Double vy;
  /**
   * [可选] 角速度.
   */
  private Double omega;

}
