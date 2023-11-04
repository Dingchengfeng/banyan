package com.standard.banyan.driver.vda5050.adapter.message.state;

import com.standard.banyan.driver.vda5050.adapter.message.common.BoundingBoxReference;
import com.standard.banyan.driver.vda5050.adapter.message.common.LoadDimensions;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 负载.
 */
@Getter
@Setter
public class Load implements Serializable {

  /**
   * [可选] 负载的唯一标识（比如条码或RFID）
   */
  private String loadId;
  /**
   * [可选] 负载类型
   */
  private String loadType;
  /**
   * [可选] 指示agv使用哪个负载单元承载
   */
  private String loadPosition;
  /**
   * [可选] 负载重量
   */
  private Double weight;
  /**
   * [可选] 边界框位置参考点
   */
  private BoundingBoxReference boundingBoxReference;
  /**
   * [可选] 负载边界框的尺寸
   */
  private LoadDimensions loadDimensions;

}
