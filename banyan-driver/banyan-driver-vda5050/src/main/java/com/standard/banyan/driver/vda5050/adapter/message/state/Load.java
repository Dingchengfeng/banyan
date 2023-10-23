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
 * Load object that describes the load if the AGV has information about it.
 */
@Data
public class Load implements Serializable {

  /**
   * [Optional] Unique identification number of the load. (E.g. barcode or RFID).
   * <p>
   * Empty, if the AGV can identify the load but didn't identify the load, yet. Optional, if the
   * AGV cannot identify the load.
   */
  private String loadId;
  /**
   * [Optional] Type of the load.
   */
  private String loadType;
  /**
   * [Optional] Indicates which load handling/carrying unit of the AGV is used. (E.g. in case the
   * AGV has multiple spots/positions to carry loads.
   * <p>
   * Examples: "front", "back", "positionC1". Optional for vehicles with only one load position.
   */
  private String loadPosition;
  /**
   * [Optional] Absolute weight of the load measured (in kg). Range: [0.0 ... infinity]
   */
  private Long weight;
  /**
   * [Optional] Point of reference for the location of the bounding box.
   * <p>
   * The point of reference is always the center of the bounding box's bottom surface
   * (at height = 0) and is described in coordinates of the AGV's coordinate system.
   */
  private BoundingBoxReference boundingBoxReference;
  /**
   * [Optional] Dimensions of the load's bounding box (in m).
   */
  private LoadDimensions loadDimensions;

}
