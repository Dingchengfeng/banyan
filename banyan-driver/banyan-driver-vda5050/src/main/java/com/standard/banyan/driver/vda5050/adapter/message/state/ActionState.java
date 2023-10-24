/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Describes the state of an action already processed, currently being process or to be processed
 * by an AGV.
 */
@Getter
@Setter
public class ActionState implements Serializable {

  /**
   * Unique action ID.
   */
  private String actionId;
  /**
   * Type of the action.
   * <p>
   * Only for informational or visualization purposes.
   */
  private String actionType;
  /**
   * [Optional] Additional information on the current action.
   */
  private String actionDescription;
  /**
   * Status of this action.
   */
  private ActionStatus actionStatus;
  /**
   * [Optional] Description of the result.
   * <p>
   * E.g. the result of a RFID-read.
   */
  private String resultDescription;

}
