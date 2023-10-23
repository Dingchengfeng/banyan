/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.visualization;

import com.standard.banyan.driver.vda5050.adapter.message.Header;
import com.standard.banyan.driver.vda5050.adapter.message.common.AgvPosition;
import com.standard.banyan.driver.vda5050.adapter.message.common.Velocity;
import lombok.Data;

/**
 * Describes information for visualization purposes.
 * <p>
 * Can be published at a higher rate if wanted. Since bandwidth may be expensive depening on the
 * update rate, all fields are optional.
 */
@Data
public class Visualization extends Header {

  /**
   * The path to the JSON schema file.
   */
  public static final String JSON_SCHEMA = "visualization.schema";

  /**
   * [Optional] Current position of the AGV on the map.
   */
  private AgvPosition agvPosition;

  /**
   * [Optional] The AGV's velocity in vehicle coordinates.
   */
  private Velocity velocity;
}
