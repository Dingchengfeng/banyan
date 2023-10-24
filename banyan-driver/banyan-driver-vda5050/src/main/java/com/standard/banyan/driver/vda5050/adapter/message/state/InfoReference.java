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
 * Holds the information reference (e.g. orderId, orderUpdateId, actionId) as key-value pairs.
 */
@Getter
@Setter
public class InfoReference implements Serializable {

  /**
   * The reference key.
   */
  private String referenceKey;
  /**
   * The reference value.
   */
  private String referenceValue;

}