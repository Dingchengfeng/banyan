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
 * 负载边界尺寸.
 * @author dingchengfeng
 */
@Getter
@Setter
public class LoadDimensions implements Serializable {

  /**
   * 长.
   */
  private Double length;
  /**
   * 框.
   */
  private Double width;
  /**
   * [可选] 高.
   */
  private Double height;


}
