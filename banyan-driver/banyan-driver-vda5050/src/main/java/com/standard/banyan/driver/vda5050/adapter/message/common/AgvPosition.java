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


@Data
public class AgvPosition implements Serializable {


  private Double x;

  private Double y;

  private Double theta;

  private String mapId;

  private String mapDescription;

  private Boolean positionInitialized;

  private Double localizationScore;

  private Double deviationRange;

}
