package com.ussp.service.impl;

import com.ussp.mapper.SysConfigMapper;
import com.ussp.pojo.SysConfig;
import com.ussp.service.AdminSysConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminSysConfigServiceImpl implements AdminSysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public SysConfig getByKey(String key) {
        return sysConfigMapper.getByKey(key);
    }

    @Override
    public void updateConfig(String key, String value) {
        int rows = sysConfigMapper.updateValue(key, value);
        if (rows == 0) {
            throw new RuntimeException("更新失败，配置不存在：" + key);
        }
    }

    @Override
    public String getConfigValue(String configKey) {
        return sysConfigMapper.getValueByKey(configKey);
    }
}
