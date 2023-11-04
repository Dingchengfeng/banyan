package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 轨迹.
 */
@Getter
@Setter
public class Trajectory implements Serializable {

  /**
   * 次数[1,∞]，默认1。为什么定义成double
   */
  private Double degree;

  /**
   * 节点向量
   */
  private List<Double> knotVector;

  /**
   * 控制点
   */
  private List<ControlPoint> controlPoints;


}
