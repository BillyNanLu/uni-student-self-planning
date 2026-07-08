package com.ussp.mapper;

import com.ussp.pojo.UserDirection;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDirectionMapper {

    @Select("SELECT COUNT(*) FROM user_direction WHERE final_direction = #{direction}")
    Long countUserByFinalDirection(String direction);

    @Insert("""
    INSERT INTO user_direction(user_id,preferred_direction)
    VALUES(#{userId},#{preferred})
    ON DUPLICATE KEY UPDATE
        preferred_direction=#{preferred}
    """)
    void insertOrUpdate(@Param("userId") Long userId,
                        @Param("preferred") String preferred);

    @Select("SELECT * FROM user_direction WHERE user_id=#{userId} ORDER BY create_time DESC LIMIT 1")
    UserDirection findByUserId(Long userId);

    @Update("UPDATE user_direction SET confirm_status=#{status}, final_direction=#{finalDirection}, reject_reason=#{rejectReason} WHERE user_id=#{userId} ORDER BY create_time DESC LIMIT 1")
    void updateDirection(@Param("userId") Long userId,
                         @Param("status") Integer status,
                         @Param("finalDirection") String finalDirection,
                         @Param("rejectReason") String rejectReason);

    @Update("UPDATE user_direction SET final_direction=#{preferred} WHERE user_id=#{userId}")
    void updateFinalDirection(Long userId, String preferred);

    @Update("UPDATE user_direction SET ai_reason=#{aiReason}, system_direction=#{systemDirection}, is_conflict=#{isConflict}, final_direction=#{finalDirection} WHERE id = #{id}")
    void updateAiReasonSystemDirectionAndISConflict(UserDirection ud);
}
