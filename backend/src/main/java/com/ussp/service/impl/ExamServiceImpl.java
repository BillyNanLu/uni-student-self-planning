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
    public List<Exam> getAllExams(Integer directionId, Integer year, Integer status) {
        if (year == null) {
            // 默认返回未来一年（如当前是2025，则返回 2025 和 2026 的）
            int thisYear = LocalDate.now().getYear();
            List<Exam> examsThisYear = examMapper.findExams(directionId, thisYear, status);
            List<Exam> examsNextYear = examMapper.findExams(directionId, thisYear + 1, status);
            examsThisYear.addAll(examsNextYear);
            return examsThisYear;
        } else {
            return examMapper.findExams(directionId, year, status);
        }
    }

    @Override
    public boolean deleteExamById(Long id) {
        // 如果返回值大于0，表示删除成功
        return examMapper.deleteExamById(id) > 0;
    }

    @Override
    public Exam addExam(Exam exam) {
        // 1. 检查是否存在同名考试
        Exam existing = examMapper.findByName(exam.getName());
        if (existing != null) {
            return null; // 已存在同名考试
        }

        // 2. 设置默认值
        if (exam.getStatus() == null) {
            exam.setStatus(1);
        }
        exam.setCreateTime(LocalDateTime.now());

        // 3. 插入数据库
        int rows = examMapper.insertExam(exam);
        if (rows > 0) {
            return examMapper.findById(exam.getId());
        }

        return null;
    }

    @Override
    public Exam updateExam(Integer id, Exam exam) {
        // 1 先检查是否存在
        Exam existing = examMapper.findById(id);
        if (existing == null) return null;

        // 2 更新字段（仅非空字段）
        exam.setId(id);
        int updated = examMapper.updateExam(exam);

        // 3 返回最新数据
        return updated > 0 ? examMapper.findById(id) : null;
    }
}
