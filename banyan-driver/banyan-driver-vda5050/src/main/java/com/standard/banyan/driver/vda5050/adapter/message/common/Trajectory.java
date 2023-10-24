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
import java.util.List;

/**
 * The trajectory of an AGV described as NURBS.
 */
@Getter
@Setter
public class Trajectory implements Serializable {

  private Double degree;

  private List<Double> knotVector;

  private List<ControlPoint> controlPoints;


}
