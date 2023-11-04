package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 安全状态
 */
@Getter
@Setter
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
