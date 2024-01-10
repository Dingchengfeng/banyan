package com.standard.banyan.kernel.traffic;

import com.tiansu.nts.rcs.model.Step;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingchengfeng
 * @description 交管段:每次下发的移动步骤
 * @date 2023/07/10
 */
@Getter
public class TrafficSegment {
    /**
     * 交管段最小长度
     */
    public static final double MIN_LENGTH = 2.0D;
    /**
     * 交管段内的运动步骤
     */
    private final List<Step> steps = new ArrayList<>(4);

    /**
     * 交管段长度
     */
    private Double length = 0D;


    public void addStep(Step step) {
        this.steps.add(step);
        this.length = this.length + step.getLength();
    }

    public void addAllSteps(List<Step> steps){
        this.steps.addAll(steps);
        this.length = this.length + steps.stream().mapToDouble(Step::getLength).sum();
    }
}
