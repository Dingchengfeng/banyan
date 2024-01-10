package com.standard.banyan.kernel.dispatch.algorithm;

import java.util.List;
import java.util.Map;

/**
 * @author dingchengfeng
 * @description 匹配算法
 * @date 2023/07/27
 */
public interface MatchAlgo {
    /**
     * 匹配
     *
     * @param firstPart 第一部分,矩阵的行
     * @param secondPart 第二部分,矩阵的列
     * @param costMatrix 成本矩阵
     * @return 匹配结果 key:amrId,value:taskId
     */
    Map<String,String> match(List<String> firstPart,List<String> secondPart,double[][] costMatrix);
}
