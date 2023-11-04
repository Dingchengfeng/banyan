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
 * 信息来源
 */
@Getter
@Setter
public class InfoReference implements Serializable {

  /**
   * 键
   */
  private String referenceKey;
  /**
   * 值
   */
  private String referenceValue;

}
