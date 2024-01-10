package com.standard.banyan.kernel.traffic.shape;

import com.tiansu.nts.rcs.model.Resource;

import java.io.Serializable;

/**
 * 图形
 *
 * @author dingchengfeng
 * @description 车体包络基础图形
 * @date 2023/07/10
 */
public interface Shape extends Serializable {

    /**
     * 检测this矩形和入参矩形是否碰撞
     *
     * @param other 另一个图形
     * @return true 碰撞；false 不碰撞
     */
    boolean collision(Shape other);

    /**
     * 获取图形对应的资源id
     *
     * @return 资源id
     */
    Resource.GlobalId getResourceId();
}
