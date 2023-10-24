/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Represents information messages.
 * <p>
 * Information messages are only for visualization/debugging.
 */
@Getter
@Setter
public class Info implements Serializable {

  /**
   * The type/name of the information.
   */
  private String infoType;
  /**
   * [Optional] Verbose description of the information.
   */
  private String infoDescription;
  /**
   * The level of the information.
   */
  private InfoLevel infoLevel;
  /**
   * [Optional] List of information references.
   */
  private List<InfoReference> infoReferences;

}
