package com.standard.banyan.driver.amr;

import com.standard.banyan.driver.amr.command.Command;
import com.standard.banyan.driver.amr.domain.AmrInfo;
import com.standard.banyan.driver.amr.domain.Movement;

import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface CommAdapter {

    /**
     * 立即执行
     * @param command
     */
    void execute(Command command);

    /**
     * 追加移动指令
     */
    void append(Movement order);

    List<Command> getCommandList();
    /**
     * 获取车辆信息
     * @return
     */
    AmrInfo getAmrInfo();

}
