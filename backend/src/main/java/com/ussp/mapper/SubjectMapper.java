package com.ussp.mapper;

import com.ussp.pojo.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {
    @Select("SELECT * FROM subject_info ORDER BY id")
    List<Subject> findAll();

    @Update("""
        UPDATE subject_info
        SET
            name = #{name},
            representativeMajors = #{representativeMajors},
            postgraduate = #{postgraduate},
            civilService = #{civilService},
            marketEmployment = #{marketEmployment}
        WHERE id = #{id}
    """)
    int updateSubject(Subject subject);
}
