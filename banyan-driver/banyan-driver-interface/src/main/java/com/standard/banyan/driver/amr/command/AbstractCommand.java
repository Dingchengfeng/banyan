package com.standard.banyan.driver.amr.command;

import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public abstract class AbstractCommand implements Command {
    private final String commandWord;

    private final String commandId;

    private final String commandDesc;

    private final Map<String, ParamDef> paramDefMap;

    protected AbstractCommand(String commandWord, String commandId, String commandDesc, Map<String, ParamDef> paramDefMap) {
        this.commandWord = commandWord;
        this.commandId = commandId;
        this.commandDesc = commandDesc;
        this.paramDefMap = paramDefMap;
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
    public Map<String, ParamDef> getCommandDef() {
        return paramDefMap;
    }
}
