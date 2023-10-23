/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ErrorEntry implements Serializable {

  /**
   * The type/name of the error.
   */
  private String errorType;
  /**
   * [Optional] List of references to identify the source of the error. (E.g. headerId, orderId,
   * actionId.)
   */
  private List<ErrorReference> errorReferences;
  /**
   * [Optional] Verbose description of the error.
   */
  private String errorDescription;
  /**
   * The error level.
   */
  private ErrorLevel errorLevel;

}
