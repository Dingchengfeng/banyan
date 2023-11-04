package com.standard.banyan.driver.vda5050.adapter.message.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 动作参数
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Getter
@Setter
public class ActionParameter implements Serializable {

  /**
   * 键
   */
  private String key;
  /**
   * 值
   */
  private Object value;

}
