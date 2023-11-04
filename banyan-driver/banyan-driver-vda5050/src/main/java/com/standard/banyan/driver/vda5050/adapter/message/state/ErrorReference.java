package com.standard.banyan.driver.vda5050.adapter.message.state;


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
