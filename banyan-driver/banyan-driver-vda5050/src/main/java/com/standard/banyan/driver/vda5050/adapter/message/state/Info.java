package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用于调试或可视化的信息
 */
@Getter
@Setter
public class Info implements Serializable {

  /**
   * 信息类型/名称
   */
  private String infoType;
  /**
   * [可选] 描述
   */
  private String infoDescription;
  /**
   * 等级
   */
  private InfoLevel infoLevel;
  /**
   * [可选] 信息来源
   */
  private List<InfoReference> infoReferences;

}
