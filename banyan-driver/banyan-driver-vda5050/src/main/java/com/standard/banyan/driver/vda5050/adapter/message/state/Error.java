package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * 错误
 * @author dingchengfeng
 */
@Getter
@Setter
public class Error implements Serializable {

  /**
   * 错误名称/类型
   */
  private String errorType;
  /**
   * [可选] 错误来源
   */
  private List<ErrorReference> errorReferences;
  /**
   * [可选] 错误描述
   */
  private String errorDescription;
  /**
   * 错误等级
   */
  private ErrorLevel errorLevel;

}
