/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Describes an AGVs battery state.
 */
@Getter
@Setter
public class BatteryState implements Serializable {

  /**
   * State of Charge (in percent). Range: [0 ... 100]
   * <p>
   * If an AGV only provides values for good or bad battery levels, these will be indicated
   * as 20% (bad) and 80% (good).
   */
  private Double batteryCharge;
  /**
   * [Optional] The battery voltage (in V).
   */
  private Double batteryVoltage;
  /**
   * [Optional] State of health (in percent). Range: [0 ... 100]
   */
  private Short batteryHealth;
  /**
   * Whether the AGV is charging or nor.
   */
  private Boolean charging;
  /**
   * [Optional] Estimated reach with actual state of charge. Range: [0 ... infinity]
   */
  private Long reach;

}
