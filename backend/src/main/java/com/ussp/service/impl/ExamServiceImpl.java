package com.ussp.service.impl;

import com.ussp.mapper.ExamMapper;
import com.ussp.pojo.Exam;
import com.ussp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
