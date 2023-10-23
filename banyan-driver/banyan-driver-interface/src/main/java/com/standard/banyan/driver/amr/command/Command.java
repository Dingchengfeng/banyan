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
     * 获取指令参数
     * @return 指令参数
     */
    Map<String,CommandParam> getCommandParams();

    class CommandParam {
        private String paramKey;
        private String valueType;
        private String paramDesc;
        private boolean isRequired;

        public CommandParam(String paramKey, String valueType, String paramDesc, boolean isRequired) {
            this.paramKey = paramKey;
            this.valueType = valueType;
            this.paramDesc = paramDesc;
            this.isRequired = isRequired;
        }
    }

}
