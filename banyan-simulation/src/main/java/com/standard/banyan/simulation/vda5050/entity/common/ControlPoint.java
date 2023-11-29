package com.standard.banyan.simulation.vda5050.entity.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 控制点
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Getter
@Setter
public class ControlPoint implements Serializable {

  /**
   * x坐标
   */
  private Double x;
  /**
   * y坐标
   */
  private Double y;
  /**
   * 权重 [0,∞],默认1.0
   */
  private Double weight;
}
