package com.standard.banyan.simulation.vda5050.entity.state;

import com.standard.banyan.simulation.vda5050.entity.Header;
import com.standard.banyan.simulation.vda5050.entity.common.AgvPosition;
import com.standard.banyan.simulation.vda5050.entity.common.Velocity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 状态
 * @author dingchengfeng
 */
@Getter
@Setter
public class State extends Header {

  public static final String JSON_SCHEMA = "state.schema";
  /**
   * 当前执行的订单id；若无，则传上一个完成的订单id；若无，则传""
   */
  private String orderId;
  /**
   * order更新id，表明该order的更新已经被AGV接受。如orderUpdateId不可用则填"0"
   */
  private Long orderUpdateId;
  /**
   * [可选] 区域集id,与order保持一致
   */
  private String zoneSetId;
  /**
   * AGV最后到达的节点id,可以为当前节点，没有就默认""
   */
  private String lastNodeId;
  /**
   * AGV最后到达的节点sequenceId,可以为当前节点，没有就默认0
   */
  private Long lastNodeSequenceId;
  /**
   * order中待完成的NodeState数组
   * 没有就默认空数组
   */
  private List<NodeState> nodeStates;
  /**
   * order中待完成遍历的EdgeState数组
   * 没有就默认空数组
   */
  private List<EdgeState> edgeStates;
  /**
   * [可选] 位置
   */
  private AgvPosition agvPosition;
  /**
   * [可选] 速度
   */
  private Velocity velocity;
  /**
   * [可选] 负载
   */
  private List<Load> loads;
  /**
   * true,表明当前agv在行驶或旋转
   */
  private Boolean driving;
  /**
   * [可选] true,agv处于暂停状态
   */
  private Boolean paused;
  /**
   * [可选] true，agv即将走完base。主控系统需要下发新的base,否则agv会减速。
   */
  private Boolean newBaseRequest;
  /**
   * [可选] 线导航车辆用来指示它已经驶过lastNodeId的距离
   */
  private Double distanceSinceLastNode;
  /**
   * 包含当前action和待执行的action的列表。action完成后，需要更新state并发布，其中actionStatus需要设置为FINISHED。
   * actionState需要一直保留，直到收到一个新的order
   */
  private List<ActionState> actionStates;
  /**
   * 电池信息
   */
  private BatteryState batteryState;
  /**
   * 操作模式
   */
  private OperatingMode operatingMode;
  /**
   * Error数组，所有当前激活的错误。
   * todo 如果是指令自身的错误，是否在下一个指令到达后清除？
   */
  private List<Error> errors;
  /**
   * Info数组
   */
  private List<Info> information;
  /**
   * 包含所有安全相关信息
   */
  private SafetyState safetyState;


}
