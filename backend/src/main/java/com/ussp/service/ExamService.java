package com.ussp.service;

import com.ussp.pojo.Exam;

import java.util.List;

public interface ExamService {
    // 获取所有考试信息
    List<Exam> getAllExams(Integer directionId, Integer year, Integer status);

    // 删除考试
    boolean deleteExamById(Long id);
}
