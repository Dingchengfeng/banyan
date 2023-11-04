package com.standard.banyan.driver.vda5050.adapter.message.state;


/**
 * 急停
 * @author dingchengfeng
 */
public enum EStop {

  /**
   * Auto-acknowledgeable e-stop is activated. (E.g. by bumper or protective field.)
   * 自动触发的急停？
   */
  AUTOACK,
  /**
   * E-stop has to be acknowledged manually at the vehicle.
   * 手动触发的急停？
   */
  MANUAL,
  /**
   * Facility e-stop has to be acknowledged remotely.
   * 远程触发的急停？
   */
  REMOTE,
  /**
   * 没有急停
   */
  NONE;
}
