package com.standard.banyan.common.api;

import com.standard.banyan.common.constant.RequestParamConstant;
import com.standard.banyan.common.enums.OrderEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求
 * @author dingchengfeng
 */
@Getter
@Setter
public class PageQuery {
    /**
     * 页码
     * @mock 1
     * */
    private Long pageNum = RequestParamConstant.PAGE_NUM_DEFAULT;
    /**
     * 页尺寸
     * @mock 10
     * */
    private Long pageSize = RequestParamConstant.PAGE_SIZE_DEFAULT;

    /**
     * 关键字
     * @mock ''
     * */
    private String keyword;

    /**
     * 正序倒序（desc为降序，asc为正序）
     * @mock desc
     * */
    private OrderEnum order;

    /**
     * 排序的列名
     * @mock gmtModified
     * */
    private String orderBy;
}
