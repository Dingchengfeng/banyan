/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Data;

import java.io.Serializable;

/**
 * Dimensions of the load's bounding box.
 */
@Data
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
