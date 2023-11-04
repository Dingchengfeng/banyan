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
 * 订单
 * @author dingchengfeng
 */
@Getter
@Setter
public class Order extends Header {

  public static final String JSON_SCHEMA = "order.schema";

  /**
   * id
   */
  private String orderId;

  /**
   * 从0开始，每次追加加1
   */
  private Long orderUpdateId;

  /**
   * [可选]，区域id
   */
  private String zoneSetId;

  /**
   * 节点
   */
  private List<Node> nodes;

  /**
   * 边
   */
  private List<Edge> edges;

}
