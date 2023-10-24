package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingchengfeng
 * @description VDA5050协议支持的功能
 * @date 2023/08/22
 */
@Getter
@Setter
public class ProtocolFeatures {
    /**
     * 支持和/或必需的可选参数列表。 此处未列出的可选参数假定 AGV 不支持
     */
    private List<OptionalParameter> optionalParameters;
    /**
     * 此AGV支持的参数的所有操作的列表
     */
    private List<AgvAction> agvActions;
    @Getter
    @Setter
    public static class OptionalParameter {
        /**
         * 可选参数的全名
         */
        private String parameter;
        /**
         * 可选参数的支持类型
         */
        private Support support;
        /**
         * 可选参数的支持类型
         */
        private String description;

        public enum Support {
            /**
             * 可选的
             */
            SUPPORTED,
            /**
             * 必须的
             */
            REQUIRED;
        }

    }

    @Getter
    @Setter
    public static class AgvAction {
        /**
         * 对应于action.actionType的唯一actionType
         */
        private String actionType;
        /**
         * 使用此action的允许范围列表。 INSTANT：立即执行，NODE：可在节点上使用，EDGE：可在边缘上使用
         */
        private List<ActionScope> actionScopes;
        /**
         * 指令动作描述
         */
        private String actionDescription;
        /**
         *  参数列表。 如果未定义，则该操作没有参数
         */
        private List<ActionParameter> actionParameters;
        /**
         * 返回结果的描述信息
         */
        private String resultDescription;
        @Getter
        @Setter
        public static class ActionParameter {
            /**
             * 用来指示车辆经过“lastNodeId”的距离。以米为单位
             */
            private String key;
            /**
             * 参数值类型
             */
            private ValueDataType valueDataType;
            /**
             * 参数描述
             */
            private String description;
            /**
             * 是否可选参数
             */
            private Boolean isOptional;
        }
    }

    /**
     * 参数的数据类型
     */
    public enum ValueDataType {
        BOOL,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        ARRAY,
        OBJECT,

    }

    /**
     * 使用此action的允许范围
     */
    public enum ActionScope {
        INSTANT,
        NODE,
        EDGE
    }
}
