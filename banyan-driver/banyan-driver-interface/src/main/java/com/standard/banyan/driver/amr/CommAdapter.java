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
     * @param command 指令
     */
    void execute(Command command);

    /**
     * 追加移动指令
     * @param order 移动指令
     */
    void append(Movement order);

    /**
     * 获取可以执行的指令集合
     * @return 可以执行的指令集合
     */
    List<Command> getCommandList();

    /**
     * 获取机器人信息
     * @return 机器人信息
     */
    AmrInfo getAmrInfo();

}
