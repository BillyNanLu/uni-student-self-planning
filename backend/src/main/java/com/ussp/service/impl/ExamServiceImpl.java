package com.ussp.service.impl;

import com.ussp.mapper.ExamMapper;
import com.ussp.pojo.Exam;
import com.ussp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<Exam> getExamList() {
        return examMapper.getExamList();
    }

    @Override
    public List<Exam> getExamByDirection(Integer directionId) {
        return examMapper.getExamByDirection(directionId);
    }

    @Override
    public void addExam(Exam exam) {
        examMapper.insertExam(exam);
    }

    @Override
    public void updateExam(Exam exam) {
        examMapper.updateExam(exam);
    }

    @Override
    public void deleteExam(Long id) {
        examMapper.deleteExam(id);
    }
}
