package com.ussp.mapper;

import com.ussp.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SysConfigMapper {

    // 根据 config_key 查询配置
    @Select("SELECT id, config_key, config_value, config_desc, create_time, update_time " +
            "FROM sys_config WHERE config_key = #{configKey}")
    SysConfig getByKey(@Param("configKey") String configKey);

    // 更新配置
    @Update("UPDATE sys_config SET config_value = #{configValue} WHERE config_key = #{configKey}")
    int updateValue(@Param("configKey") String configKey,
                    @Param("configValue") String configValue);

    // 根据 config_key 查询配置值
    @Select("SELECT config_value FROM sys_config WHERE config_key = #{key}")
    String getValueByKey(String key);
}
