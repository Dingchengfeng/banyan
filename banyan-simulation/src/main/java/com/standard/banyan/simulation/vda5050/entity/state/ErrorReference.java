package com.standard.banyan.simulation.vda5050.entity.state;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 错误来源
 * @author dingchengfeng
 */
@Getter
@Setter
public class ErrorReference implements Serializable {

  /**
   * 来源字段
   */
  private String referenceKey;
  /**
   * 字段值
   */
  private String referenceValue;


}
