package com.standard.banyan.kernel.route.step;

import com.tiansu.nts.rcs.model.Step;

/**
 * @author dingchengfeng
 * @description 抽象步骤
 * @date 2023/08/17
 */
public abstract class AbstractStep implements Step {
    private Integer index;



    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }
}
