package com.ussp.mapper;

import com.ussp.pojo.Task;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TaskMapper {

    /**
     * 查询当前用户的任务列表（未逻辑删除）
     */
    @Select("""
        SELECT
            id,
            user_id,
            category,
            content,
            priority,
            due_date,
            complete_time,
            status,
            create_time,
            update_time,
            is_delete
        FROM task
        WHERE user_id = #{userId}
          AND is_delete = 0
        ORDER BY create_time DESC
    """)
    List<Task> selectByUserId(@Param("userId") Long userId);


    /**
     * 新增任务
     */
    @Insert("""
        INSERT INTO task (
            user_id,
            category,
            content,
            priority,
            due_date,
            status,
            is_delete,
            create_time,
            update_time
        ) VALUES (
            #{userId},
            #{category},
            #{content},
            #{priority},
            #{dueDate},
            #{status},
            #{isDelete},
            NOW(),
            NOW()
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTask(Task task);


    /**
     * 根据 ID 查询任务
     */
    @Select("""
        SELECT
            id,
            user_id,
            category,
            content,
            priority,
            due_date,
            complete_time,
            status,
            create_time,
            update_time,
            is_delete
        FROM task
        WHERE id = #{id}
          AND is_delete = 0
    """)
    Task selectById(@Param("id") Long id);


    /**
     * 更新任务（内容 / 分类 / 优先级 / 截止日期）
     * 只更新非空字段
     */
    @Update("""
        <script>
        UPDATE task
        <set>
            <if test="content != null">
                content = #{content},
            </if>
            <if test="category != null">
                category = #{category},
            </if>
            <if test="priority != null">
                priority = #{priority},
            </if>
            <if test="dueDate != null">
                due_date = #{dueDate},
            </if>
            update_time = NOW()
        </set>
        WHERE id = #{id}
          AND is_delete = 0
        </script>
    """)
    int updateTask(Task task);


    /**
     * 更新任务状态（完成 / 取消完成）
     */
    @Update("""
        UPDATE task
        SET
            status = #{status},
            complete_time = #{completeTime},
            update_time = NOW()
        WHERE id = #{id}
          AND is_delete = 0
    """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") Integer status,
            @Param("completeTime") LocalDateTime completeTime
    );


    /**
     * 逻辑删除任务
     */
    @Update("""
        UPDATE task
        SET
            is_delete = 1,
            update_time = NOW()
        WHERE id = #{id}
    """)
    int logicDelete(@Param("id") Long id);
}
