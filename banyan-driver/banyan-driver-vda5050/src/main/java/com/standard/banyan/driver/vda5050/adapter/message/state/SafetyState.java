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
 * Holds information about the safety status.
 */
@Data
public class SafetyState implements Serializable {

  /**
   * Acknowledge-type of e-stop.
   */
  private EStop eStop;
  /**
   * Protective field violation.
   * <p>
   * {@code true}, if the field is violated.
   */
  private Boolean fieldViolation;


}
