package com.standard.banyan.driver.amr.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
@Getter
@AllArgsConstructor
public class AmrInfo {
    /**
     * 制造商
     */
    private String manufacturer;
    /**
     * 唯一序列号
     */
    private String serialNumber;

}
