/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class Action implements Serializable {

  private String actionType;

  private String actionId;

  private String actionDescription;

  private BlockingType blockingType;

  private List<ActionParameter> actionParameters;

  public enum BlockingType {

    /**
     * Action can be executed in parallel with other actions and while the vehicle is driving.
     */
    NONE,
    /**
     * Action can be executed in parallel with other actions. Vehicle must not drive.
     */
    SOFT,
    /**
     * Action must not be executed in parallel with other actions. Vehicle must not drive.
     */
    HARD;
  }

}
