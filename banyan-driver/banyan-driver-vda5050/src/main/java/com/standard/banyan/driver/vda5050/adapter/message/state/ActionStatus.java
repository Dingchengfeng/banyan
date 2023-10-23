/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;


/**
 * Defines the different state of an {@link org.opentcs.commadapter.vehicle.vda5050.v2_0.message.state.ActionState}.
 */
public enum ActionStatus {

  /**
   * Waiting.
   */
  WAITING,
  /**
   * Initializing.
   */
  INITIALIZING,
  /**
   * Running.
   */
  RUNNING,
  /**
   * Finished.
   */
  FINISHED,
  /**
   * Failed.
   */
  FAILED;
}
