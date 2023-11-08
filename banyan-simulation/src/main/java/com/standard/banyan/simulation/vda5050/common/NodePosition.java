package com.standard.banyan.simulation.vda5050.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 节点位置
 * 要求不同楼层的地图坐标原点的垂直投影重合
 */
@Getter
@Setter
public class NodePosition implements Serializable {

  /**
   * x坐标
   */
  private Double x;
  /**
   * y坐标
   */
  private Double y;
  /**
   * 角度[-π,π]
   * [可选]对于自己规划路径的agv
   */
  private Double theta;
  /**
   * [可选] 允许的位置偏差
   */
  private Double allowedDeviationXY;
  /**
   * [可选] 允许的角度偏差
   */
  private Double allowedDeviationTheta;
  /**
   * 地图id
   */
  private String mapId;
  /**
   * [可选] 地图描述
   */
  private String mapDescription;


}
