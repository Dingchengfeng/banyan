package com.standard.banyan.driver.amr;

import com.standard.banyan.driver.amr.domain.AmrInfo;
import com.standard.banyan.driver.amr.domain.DriverConfig;

import java.util.List;

/**
 * @author dingchengfeng
 * @date 2023/10/21
 **/
public interface CommAdapterFactory {

    void init(DriverConfig driverConfig,DriverMessageHandler d);

    List<String> getAdapterKeys();

    boolean canProvideAdapter(String adapterKey);

    CommAdapter createAdapter(AmrInfo amrInfo);

    void destroy();
}
