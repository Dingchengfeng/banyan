package com.standard.banyan.simulation.vda5050.entity.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 信息来源
 * @author dingchengfeng
 */
@Getter
@Setter
public class InfoReference implements Serializable {

  /**
   * 键
   */
  private String referenceKey;
  /**
   * 值
   */
  private String referenceValue;

}
