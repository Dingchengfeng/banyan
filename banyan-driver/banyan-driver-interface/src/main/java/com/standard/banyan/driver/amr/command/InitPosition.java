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
    private Map<String,Object> params;
    private static final Map<String,ParamDef> PARAM_DEF_MAP = new HashMap<>(8);
    static {
        PARAM_DEF_MAP.put("x",new ParamDef("x", ParamDef.ValueType.STRING,"x坐标",true));
        PARAM_DEF_MAP.put("y",new ParamDef("y", ParamDef.ValueType.FLOAT,"y坐标",true));
        PARAM_DEF_MAP.put("theta",new ParamDef("theta", ParamDef.ValueType.FLOAT,"角度",true));
        PARAM_DEF_MAP.put("mapId",new ParamDef("mapId", ParamDef.ValueType.STRING,"地图id",true));
        PARAM_DEF_MAP.put("lastNodeId",new ParamDef("lastNodeId", ParamDef.ValueType.STRING,"最后所在节点",true));
    }

    public InitPosition(String commandId, Map<String,Object> params) {
        super(COMMAND,commandId,DESC,PARAM_DEF_MAP);
        this.params = params;
    }

}
