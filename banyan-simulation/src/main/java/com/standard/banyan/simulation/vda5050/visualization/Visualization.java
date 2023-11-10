package com.standard.banyan.simulation.vda5050.visualization;


import com.standard.banyan.simulation.vda5050.Header;
import com.standard.banyan.simulation.vda5050.common.AgvPosition;
import com.standard.banyan.simulation.vda5050.common.Velocity;
import lombok.Getter;
import lombok.Setter;

/**
 * 可视化
 * @author dingchengfeng
 */
@Getter
@Setter
public class Visualization extends Header {

  public static final String JSON_SCHEMA = "visualization.schema";

  /**
   * [可选] 位置
   */
  private AgvPosition agvPosition;

  /**
   * [可选] 速度
   */
  private Velocity velocity;
}
