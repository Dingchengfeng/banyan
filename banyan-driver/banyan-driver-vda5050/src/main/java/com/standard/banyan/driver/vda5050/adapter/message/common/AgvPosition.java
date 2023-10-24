/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AgvPosition implements Serializable {

  private Boolean positionInitialized;

  private Double x;

  private Double y;

  private Double theta;

  private String mapId;

  private String mapDescription;

  private Double localizationScore;

  private Double deviationRange;

}
