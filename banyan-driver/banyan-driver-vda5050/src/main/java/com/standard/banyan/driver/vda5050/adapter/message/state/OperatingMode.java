/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

/**
 * Defines the operating modes of an AGV.
 */
public enum OperatingMode {

  /**
   * AGV is under full control of the master control.
   * <p>
   * AGV drives and executes actions based on orders from the master control.
   */
  AUTOMATIC,
  /**
   * AGV is under control of the master control.
   * <p>
   * AGV drives and executes actions based on orders from the master control. The driving speed is
   * controlled by the HMI (speed can't exceed the speed of automatic mode). The steering is under
   * automatic control (non-safe HMI possible).
   */
  SEMIAUTOMATIC,
  /**
   * Master control is not in control of the AGV.
   * <p>
   * Supervisor doesn't send driving order or actions to the AGV. HMI can be used to control the
   * steering and velocity and handling device of the AGV. Location of the AGV is send to the master
   * control. When AGV enters or leaves this mode, it immediately clears all the orders (safe HMI
   * required).
   */
  MANUAL,
  /**
   * Master control is not in control of the AGV.
   * <p>
   * Master control doesn't send driving order or actions to the AGV. Authorized personal can
   * reconfigure the AGV.
   */
  SERVICE,
  /**
   * Master control is not in control of the AGV.
   * <p>
   * Supervisor doesn't send driving order or actions to the AGV. The AGV is being taught, e.g.
   * mapping is done by a master control.
   */
  TEACHIN;
}
