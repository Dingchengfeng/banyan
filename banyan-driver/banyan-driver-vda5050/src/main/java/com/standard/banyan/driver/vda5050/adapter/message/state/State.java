/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.standard.banyan.driver.vda5050.adapter.message.state;

import com.standard.banyan.driver.vda5050.adapter.message.Header;
import com.standard.banyan.driver.vda5050.adapter.message.common.AgvPosition;
import com.standard.banyan.driver.vda5050.adapter.message.common.Velocity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class State extends Header {

  /**
   * The path to the JSON schema file.
   */
  public static final String JSON_SCHEMA = "state.schema";
  /**
   * Unique order identification of the current order or the previous finished order.
   * <p>
   * The {@code orderId} is kept until a new order is received. Empty string if no previous
   * {@code orderId} is available.
   */
  private String orderId;
  /**
   * Order update identification to identify that an order update has been accepted by the AGV.
   * <p>
   * {@code 0}, if no previous {@code orderUpdateId} is available.
   */
  private Long orderUpdateId;
  /**
   * [Optional] Unique ID of the zone set that the AGV currently uses for path planning.
   * <p>
   * Must be the same as the one used in the order, otherwise the AGV is to reject the order. If the
   * AGV does not use zones, this field can be omitted.
   */
  private String zoneSetId;
  /**
   * {@code nodeId} of the last reached node or, if AGV is currently on a node, {@code nodeId} of
   * the current node.
   * <p>
   * Empty string if no {@code lastNodeId} is available.
   */
  private String lastNodeId;
  /**
   * {@code sequenceId} of the last reached node or, if the AGV is currently on a node,
   * {@code sequenceId} of the current node.
   * <p>
   * {@code 0}, if no {@code lastNodeSequenceId} is available.
   */
  private Long lastNodeSequenceId;
  /**
   * List of {@link NodeState} objects that need to be traversed for fulfilling the order.
   * <p>
   * Empty, if the AGV is idle.
   */
  private List<NodeState> nodeStates;
  /**
   * List of {@link EdgeState} objects that need to be traversed for fulfilling the order.
   * <p>
   * Empty, if the AGV is idle.
   */
  private List<EdgeState> edgeStates;
  /**
   * [Optional] Current position of the AGV on the map.
   * <p>
   * Can only be omitted for AGVs without the capability to localize themselves, e.g. line-guided
   * AGVs.
   */
  private AgvPosition agvPosition;
  /**
   * [Optional] The AGV's velocity in vehicle coordinates.
   */
  private Velocity velocity;
  /**
   * [Optional] Loads that are currently handled by the AGV.
   * <p>
   * If the AGV cannot reason about load state, leave the list out of the state. If the AGV can
   * reason about the load state, but the list is empty, the AGV is considered unloaded.
   */
  private List<Load> loads;
  /**
   * Whether the AGV is driving and/or rotating.
   * <p>
   * Other movements of the AGV (e.g. lift movements) are not included here.
   */
  private Boolean driving;
  /**
   * [Optional] Whether the AGV is currently in a paused state.
   * <p>
   * An AGV can be in a paused state e.g. because of the push of a physical button on the AGV or
   * because of an instant action.
   */
  private Boolean paused;
  /**
   * [Optional] Whether the AGV is requesting a new base or not.
   * <p>
   * {@code true}, if the AGV is almost at the end of the base and will reduce speed if no new base
   * is transmitted.
   */
  private Boolean newBaseRequest;
  /**
   * [Optional] Used by line guided vehicles to indicate the distance it has been driving past
   * the {@code lastNodeId} (in m).
   */
  private Double distanceSinceLastNode;
  /**
   * List of the current actions and the actions which are yet to be finished.
   * <p>
   * This may include actions from previous nodes that are still in progress. When an action is
   * completed, an updated state message is published with the actions {@code actionStatus} set to
   * finished and if applicable with the corresponding {@code resultDescription}. The action state
   * is kept until a new order is received.
   */
  private List<ActionState> actionStates;
  /**
   * Contains all battery-related information.
   */
  private BatteryState batteryState;
  /**
   * Current operating mode of the AGV.
   */
  private OperatingMode operatingMode;
  /**
   * List of {@link Error} objects.
   * <p>
   * All active errors of the AGV should be in this list. An empty array indicates that the AGV has
   * no active errors.
   */
  private List<Error> errors;
  /**
   * List of {@link Info} objects.
   * <p>
   * This should only be used for visualization or debugging – it must not be used for logic in
   * master control. An empty list indicates that the AGV has no information.
   */
  private List<Info> information;
  /**
   * Contains all safety-related information.
   */
  private SafetyState safetyState;


}
