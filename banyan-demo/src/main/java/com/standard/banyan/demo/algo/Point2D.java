package com.standard.banyan.demo.algo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dingchengfeng
 * @date 2023/5/16
 **/
@Data
@AllArgsConstructor
public class Point2D {
    Double x;
    Double y;

    public Point2D(Integer x, Integer y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }
}
