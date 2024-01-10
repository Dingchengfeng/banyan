package com.standard.banyan.kernel.route;

import com.standard.banyan.common.model.Step;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingchengfeng
 * @description 路由
 * @date 2023/08/08
 */
@Getter
public class Route {

    private final List<Step> steps;

    private Double costs = 0D;

    public Route(List<Step> steps) {
        this.steps = steps;
        refreshIndex();
        calculateCosts();
    }

    public Route() {
        steps = new ArrayList<>();
    }

    private void calculateCosts() {
        for (Step step : steps) {
            costs = costs + step.getCost();
        }
    }

    public void refreshIndex() {
        int i = 0;
        for (Step step : steps) {
            step.setIndex(i++);
        }
    }

}
