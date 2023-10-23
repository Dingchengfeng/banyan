/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;


public enum EStop {

  /**
   * Auto-acknowledgeable e-stop is activated. (E.g. by bumper or protective field.)
   */
  AUTOACK,
  /**
   * E-stop has to be acknowledged manually at the vehicle.
   */
  MANUAL,
  /**
   * Facility e-stop has to be acknowledged remotely.
   */
  REMOTE,
  /**
   * No e-stop activated.
   */
  NONE;
}
