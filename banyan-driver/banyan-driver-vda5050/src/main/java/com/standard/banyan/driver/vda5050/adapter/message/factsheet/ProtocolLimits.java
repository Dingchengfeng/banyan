package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author dengxx
 * @description AGV的协议限制
 * @date 2023/08/22
 */
@Data
public class ProtocolLimits {

    /**
     * 字符串的最大长度
     */
    private MaxStringLens maxStringLens;

    /**
     * 数组最大长度
     */
    private MaxArrayLens maxArrayLens;

    /**
     * 发送消息时间间隔
     */
    private Timing timing;

    @Data
    public static class MaxStringLens {
        /**
         * MQTT消息最大长度
         */
        private Integer msgLen;
        /**
         * MQTT 主题中序列号部分( serial-number)的最大长度
         */
        private Integer topicSerialLen;
        /**
         * MQTT消息Topic其余部分的最大长度.
         */
        private Integer topicElemLen;
        /**
         * ID相关字段的最大长度
         */
        private Integer idLen;
        /**
         * TRUE：ID相关的字符串只包含数字值
         */
        private Boolean idNumericalOnly;
        /**
         * 枚举值的最大长度
         */
        private Integer enumLen;
        /**
         * loadId 字符串的最大长度
         */
        private Integer loadIdLen;
    }

    @Data
    public static class MaxArrayLens {
        /**
         * order消息中一次包含的nodes集合的最大个数
         */
        @JSONField(name = "order.nodes")
        private Integer orderNodes;
        /**
         * order消息中一次包含的edges集合的最大个数
         */
        @JSONField(name = "order.edges")
        private Integer orderEdges;
        /**
         * order消息中每个node包含的actions集合的最大个数
         */
        @JSONField(name = "node.actions")
        private Integer nodeActions;
        /**
         * order消息中每个edge包含的actions集合的最大个数
         */
        @JSONField(name = "edge.actions")
        private Integer edgeActions;
        /**
         * action操作的最大参数个数
         */
        @JSONField(name = "actions.actionsParameters")
        private Integer actionsActionsParameters;
        /**
         * 每次下发即时指令时，下发的最大个数
         */
        @JSONField(name = "instantActions")
        private Integer instantActions;
        /**
         * AGV 可处理的每条轨迹的最大节数
         */
        @JSONField(name = "trajectory.knotVector")
        private Integer trajectoryKnotVector;
        /**
         * 控制点的最大个数
         */
        @JSONField(name = "trajectory.controlPoints")
        private Integer trajectoryControlPoints;
        /**
         * state消息中节点状态最大个数
         */
        @JSONField(name = "state.nodeStates")
        private Integer stateNodeStates;
        /**
         * state消息中边状态最大个数
         */
        @JSONField(name = "state.edgeStates")
        private Integer stateEdgeStates;
        /**
         * state消息中负载信息状态最大个数
         */
        @JSONField(name = "state.loads")
        private Integer stateLoads;
        /**
         * state消息中动作指令执行状态最大个数
         */
        @JSONField(name = "state.actionStates")
        private Integer stateActionStates;
        /**
         * state消息中错误信息的最大个数
         */
        @JSONField(name = "state.errors")
        private Integer stateErrors;
        /**
         * state消息中调试可视化信息的最大个数
         */
        @JSONField(name = "state.information")
        private Integer stateInformation;
        /**
         * state消息中错误引用的最大个数
         */
        @JSONField(name = "error.errorReferences")
        private Integer errorErrorReferences;
        /**
         * state消息中调试可视化信息中引用的最大个数
         */
        @JSONField(name = "information.infoReferences")
        private Integer informationInfoReferences;
    }

    @Data
    public static class Timing {
        /**
         * 向 AGV 发送订单order类消息的最小间隔，单位s
         */
        private Integer minOrderInterval;
        /**
         * state类消息发送的最小间隔，单位s
         */
        private Integer minStateInterval;
        /**
         * 发送状态消息的默认间隔如果未定义，则使用主文档中的默认值
         */
        private Integer defaultStateInterval;
        /**
         * visualization类消息的发送默认间隔
         */
        private Integer visualizationInterval;
    }
}
