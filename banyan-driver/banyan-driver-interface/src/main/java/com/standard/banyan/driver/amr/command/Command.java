package com.standard.banyan.driver.amr.command;

import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface Command {
    String getCommandWord();

    String getCommandId();

    String getCommandDesc();

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
