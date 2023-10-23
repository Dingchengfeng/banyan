package com.standard.banyan.driver.amr.command;

import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public abstract class AbstractCommand implements Command {
    private String commandWord;

    private String commandId;

    private String commandDesc;

    private Map<String,CommandParam> commandParamMap;

    public AbstractCommand(String commandWord, String commandId, String commandDesc, Map<String,CommandParam> commandParamMap) {
        this.commandWord = commandWord;
        this.commandId = commandId;
        this.commandDesc = commandDesc;
        this.commandParamMap = commandParamMap;
    }

    @Override
    public String getCommandWord() {
        return commandWord;
    }

    @Override
    public String getCommandDesc() {
        return commandDesc;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public Map<String,CommandParam> getCommandParams() {
        return commandParamMap;
    }
}
