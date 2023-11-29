package com.standard.banyan.simulation.vda5050.entity.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 位置
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Getter
@Setter
public class AgvPosition implements Serializable {

  /**
   * x坐标
   */
  private Double x;

  /**
   * y坐标
   */
  private Double y;

  /**
   * 角度：[-π,π]
   */
  private Double theta;

  /**
   * 地图id,跨楼层的地图最好在同一个空间坐标系中
   */
  private String mapId;

  /**
   * [可选]地图描述
   */
  private String mapDescription;

  /**
   * 位置是否初始化
   */
  private Boolean positionInitialized;

  /**
   * [可选] 定位置信度，Range:[0.0,1.0]
   */
  private Double localizationScore;

  /**
   * [可选] 位置偏差范围
   */
  private Double deviationRange;

}
