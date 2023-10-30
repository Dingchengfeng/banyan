/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.order;


import com.standard.banyan.driver.vda5050.adapter.message.Header;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Defines an order sent from master control to the AGV.
 */
@Getter
@Setter
@Builder
public class Order extends Header {
  public Order() {
  }

  /**
   * The path to the JSON schema file.
   */
  public static final String JSON_SCHEMA = "order.schema";

  private String orderId;

  private Long orderUpdateId;

  private String zoneSetId;

  private List<Node> nodes;

  private List<Edge> edges;

}
