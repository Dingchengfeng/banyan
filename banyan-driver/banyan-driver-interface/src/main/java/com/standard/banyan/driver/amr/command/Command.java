package com.standard.banyan.driver.amr.command;

import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface Command {
    /**
     * 获取指令字
     * @return 指令字
     */
    String getCommandWord();

    /**
     * 获取指令id
     * @return 指令id
     */
    String getCommandId();

    /**
     * 获取指令描述
     * @return 指令描述
     */
    String getCommandDesc();

    /**
     * 获取参数定义
     * @return 参数定义
     */
    Map<String, ParamDef> getCommandDef();

    class ParamDef {
        private String paramKey;
        private ValueType valueType;
        private String paramDesc;
        private boolean isOptional;

        public ParamDef(String paramKey, ValueType valueType, String paramDesc, boolean isOptional) {
            this.paramKey = paramKey;
            this.valueType = valueType;
            this.paramDesc = paramDesc;
            this.isOptional = isOptional;
        }

        public enum ValueType {
            STRING,
            FLOAT
        }
    }

}
