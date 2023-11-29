package com.standard.banyan.simulation.vda5050.entity.state;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 安全状态
 * @author dingchengfeng
 */
@Getter
@Setter
@AllArgsConstructor
public class SafetyState implements Serializable {

  /**
   * 急停确认类型
   */
  private EStop eStop;
  /**
   * Protective field violation.
   * <p>
   * {@code true}, if the field is violated.
   */
  private Boolean fieldViolation;


}
