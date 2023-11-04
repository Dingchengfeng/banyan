package com.standard.banyan.driver.vda5050.adapter.message.visualization;

import com.standard.banyan.driver.vda5050.adapter.message.Header;
import com.standard.banyan.driver.vda5050.adapter.message.common.AgvPosition;
import com.standard.banyan.driver.vda5050.adapter.message.common.Velocity;
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
