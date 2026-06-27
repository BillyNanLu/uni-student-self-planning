package com.ussp.service;

import com.ussp.pojo.SysConfig;

public interface AdminSysConfigService {

    /**
     * 获取配置
     */
    SysConfig getByKey(String key);

    /**
     * 更新配置
     */
    void updateConfig(String key, String value);

    String getConfigValue(String aiServiceEnable);
}
