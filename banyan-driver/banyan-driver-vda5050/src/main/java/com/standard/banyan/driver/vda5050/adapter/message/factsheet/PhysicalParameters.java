package com.standard.banyan.driver.vda5050.adapter.message.factsheet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingchengfeng
 * @description 规定AGV的基本物理特性的一些参数
 * @date 2023/08/22
 */
@Getter
@Setter
public class PhysicalParameters {
    /**
     * 最小速度 单位m/s
     */
    private Double speedMin;
    /**
     * 最大速度 单位m/s
     */
    private Double speedMax;
    /**
     * 最大加速度,m/s^2
     */
    private Double accelerationMax;
    /**
     * 最大减速度,m/s^2
     */
    private Double decelerationMax;
    /**
     * 最小高度，单位m
     */
    private Double heightMin;
    /**
     * 最大高度，单位m
     */
    private Double heightMax;
    /**
     * 宽度,单位m
     */
    private Double width;
    /**
     * 长度,单位m
     */
    private Double length;


}
