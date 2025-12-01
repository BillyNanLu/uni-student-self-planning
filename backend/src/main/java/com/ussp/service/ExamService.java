package com.ussp.service;

import com.ussp.pojo.Exam;

import java.util.List;

public interface ExamService {
    List<Exam> getExamList();

    List<Exam> getExamByDirection(Integer directionId);

    void addExam(Exam exam);

    void updateExam(Exam exam);

    void deleteExam(Long id);
}
