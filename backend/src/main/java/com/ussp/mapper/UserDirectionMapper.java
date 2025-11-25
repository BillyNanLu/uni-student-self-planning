package com.ussp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDirectionMapper {

    @Insert("""
    INSERT INTO user_direction(user_id,preferred_direction,system_direction,is_conflict)
    VALUES(#{userId},#{preferred},#{system},#{conflict})
    ON DUPLICATE KEY UPDATE
        preferred_direction=#{preferred},
        system_direction=#{system},
        is_conflict=#{conflict}
    """)
    void insertOrUpdate(@Param("userId") Long userId,
                        @Param("preferred") String preferred,
                        @Param("system") String system,
                        @Param("conflict") int conflict);
}
