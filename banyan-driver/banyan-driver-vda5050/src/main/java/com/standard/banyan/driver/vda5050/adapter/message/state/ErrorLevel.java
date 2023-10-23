/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

/**
 * Defines various error levels.
 */
public enum ErrorLevel {

  /**
   * AGV is ready to start. (E.g. maintenance cycle expiration warning.)
   */
  WARNING,
  /**
   * AGV is not in running condition, user intervention required. (E.g. laser scanner is
   * contaminated.)
   */
  FATAL;
}
