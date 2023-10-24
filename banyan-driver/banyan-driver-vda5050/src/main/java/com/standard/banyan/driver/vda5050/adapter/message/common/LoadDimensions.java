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
 * Dimensions of the load's bounding box.
 */
@Getter
@Setter
public class LoadDimensions implements Serializable {

  /**
   * Absolute length of the loads bounding box (in m).
   */
  private Double length;
  /**
   * Absolute width of the loads bounding box (in m).
   */
  private Double width;
  /**
   * [Optional] Absolute height of the loads bounding box (in m).
   */
  private Double height;


}
