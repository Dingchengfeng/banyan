package com.standard.banyan.simulation.vda5050.msghandler;

import com.alibaba.fastjson.JSON;
import com.standard.banyan.simulation.vda5050.Vda5050Robot;
import com.standard.banyan.simulation.vda5050.entity.instantactions.InstantActions;

/**
 * @author dingchengfeng
 * @date 2023/11/08
 */
public class InstantActionsMsgHandler implements MessageHandler {
    private final Vda5050Robot vda5050Robot;

    public InstantActionsMsgHandler(Vda5050Robot vda5050Robot) {
        this.vda5050Robot = vda5050Robot;
    }

    @Override
    public void handleMessage(String message) {
        InstantActions instantActions = JSON.parseObject(message, InstantActions.class);
        vda5050Robot.handInstantActions(instantActions);
    }
}
