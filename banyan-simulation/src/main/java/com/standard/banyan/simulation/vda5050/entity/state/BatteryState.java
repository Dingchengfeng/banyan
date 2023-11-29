/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.simulation.vda5050.entity.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 电池状态
 * @author dingchengfeng
 */
@Getter
@Setter
public class BatteryState implements Serializable {

  /**
   * 电量，[0.0,100.0]
   *
   */
  private Double batteryCharge;
  /**
   * [可选] 电压
   */
  private Double batteryVoltage;
  /**
   * [可选] 健康度,[0,100]
   */
  private Short batteryHealth;
  /**
   * 是否充电中
   */
  private Boolean charging;
  /**
   * [可选] Estimated reach with actual state of charge. Range: [0,∞]
   */
  private Long reach;

}
