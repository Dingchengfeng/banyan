package com.standard.banyan.driver.vda5050.adapter.message.instantactions;

import com.standard.banyan.driver.vda5050.adapter.message.Header;
import com.standard.banyan.driver.vda5050.adapter.message.common.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingchengfeng
 * @description TODO
 * @date 2023/10/23
 */
@Getter
@Setter
public class InstantActions extends Header {
    public static final String JSON_SCHEMA = "instantActions.schema";
    private List<Action> actions;
}
