/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Additional parameters for an {@link Action}.
 */
@Getter
@Setter
public class ActionParameter implements Serializable {

  /**
   * The key of the action parameter.
   */
  private String key;
  /**
   * The value of the action parameter.
   * Can be an array, boolean, number or string.
   */
  private Object value;

}
