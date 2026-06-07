package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.SysConfig;
import com.ussp.service.AdminSysConfigService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/sys-config")
public class AdminSysConfigController {

    @Resource
    private AdminSysConfigService sysConfigService;

    /**
     * 获取 AI 服务开关配置
     */
    @GetMapping("/ai-service-enable")
    public Result<SysConfig> getAISwitchConfig() {
        SysConfig config = sysConfigService.getByKey("ai_service_enable");
        return Result.success(config);
    }

    /**
     * 更新配置值
     */
    @PutMapping("/update")
    public Result<String> updateConfig(@RequestBody Map<String, String> params) {
        String key = params.get("config_key");
        String value = params.get("config_value");

        if (key == null || value == null) {
            return Result.error("参数缺失");
        }

        sysConfigService.updateConfig(key, value);
        return Result.success("更新成功");
    }
}
