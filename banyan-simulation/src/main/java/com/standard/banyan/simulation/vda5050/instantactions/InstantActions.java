package com.standard.banyan.simulation.vda5050.instantactions;

import com.standard.banyan.simulation.vda5050.Header;
import com.standard.banyan.simulation.vda5050.common.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author dingchengfeng
 * @date 2023/10/23
 */
@Getter
@Setter
public class InstantActions extends Header {
    public static final String JSON_SCHEMA = "instantActions.schema";

    /**
     * 动作集合
     */
    private List<Action> actions;
}
