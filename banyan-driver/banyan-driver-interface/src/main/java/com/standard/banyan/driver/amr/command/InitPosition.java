package com.standard.banyan.driver.amr.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public class InitPosition extends AbstractCommand {
    private static final String COMMAND = "initPosition";
    private static final String DESC = "初始化位置信息";
    private static final List<CommandParam> COMMAND_PARAM_LIST = new ArrayList<>();
    static {
        COMMAND_PARAM_LIST.add(new CommandParam("x","Float","x坐标",true));
        COMMAND_PARAM_LIST.add(new CommandParam("y","Float","y坐标",true));
        COMMAND_PARAM_LIST.add(new CommandParam("theta","Float","角度",true));
        COMMAND_PARAM_LIST.add(new CommandParam("mapId","Sting","地图id",true));
        COMMAND_PARAM_LIST.add(new CommandParam("lastNodeId","Sting","最后所在节点",true));
    }

    public InitPosition(String commandId, Map<String,Object> params) {
        super(COMMAND,commandId,DESC,buildParam(params));
    }

    private static Map<String,CommandParam> buildParam(Map<String,Object> params){
        return new HashMap<>(8);
    }

}
