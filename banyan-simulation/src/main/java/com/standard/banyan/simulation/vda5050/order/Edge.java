package com.standard.banyan.simulation.vda5050.order;

import com.standard.banyan.simulation.vda5050.common.Action;
import com.standard.banyan.simulation.vda5050.common.Trajectory;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Edge implements Serializable {

  /**
   * 唯一id
   */
  private String edgeId;
  /**
   * 每个order从0开始，随node和edge递增
   */
  private Long sequenceId;
  /**
   * [可选] 描述
   */
  private String edgeDescription;
  /**
   * 是否已获得路权
   */
  private Boolean released;
  /**
   * 起点
   */
  private String startNodeId;
  /**
   * 终点
   */
  private String endNodeId;
  /**
   * [可选] 最大线速度
   */
  private Double maxSpeed;
  /**
   * [可选] 最大高度
   */
  private Double maxHeight;
  /**
   * [可选] 最小高度
   */
  private Double minHeight;
  /**
   * [可选] 在边上行驶的车头朝向
   */
  private Double orientation;
  /**
   * 朝向类型
   */
  private OrientationType orientationType;
  /**
   * [可选] 线导航车辆在路口设置方向
   */
  private String direction;
  /**
   * [可选] 边上是否允许旋转.
   */
  private Boolean rotationAllowed;
  /**
   * [可选] 最大角速度.
   */
  private Double maxRotationSpeed;
  /**
   * [可选] edge的长度，线导航车用于判段何时减速.
   */
  private Double length;
  /**
   * [可选] 轨迹
   */
  private Trajectory trajectory;

  /**
   * 动作
   */
  private List<Action> actions;

  public enum OrientationType {

    /**
     * 车头方向为相对于地图X轴正方向
     */
    GLOBAL,
    /**
     * 车头方向为相对于边的切线方向，默认值
     */
    TANGENTIAL;
  }

}
