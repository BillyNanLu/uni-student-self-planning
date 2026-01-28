package com.ussp.service;

import com.ussp.dto.ExamQueryDTO;
import com.ussp.pojo.Exam;
import com.ussp.pojo.PageResult;

import java.util.List;

public interface ExamService {
    List<Exam> getExamList();

    List<Exam> getExamByDirection(Integer directionId);

    PageResult<Exam> page(ExamQueryDTO query);

    Exam getById(Integer id);

    void add(Exam exam);

    void update(Exam exam);

    void delete(Integer id);

    void updateStatus(Integer id, Integer status);
}