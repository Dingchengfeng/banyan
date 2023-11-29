package com.standard.banyan.simulation.vda5050.entity.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 边界框位置参考点
 */
@Getter
@Setter
public class BoundingBoxReference  implements Serializable {

  /**
   * x坐标
   */
  private Double x;
  /**
   * y坐标
   */
  private Double y;
  /**
   * z坐标
   */
  private Double z;
  /**
   * [可选] 角度
   */
  private Double theta;

}
