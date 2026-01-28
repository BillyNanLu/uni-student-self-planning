package com.ussp.service.impl;

import com.ussp.dto.ExamQueryDTO;
import com.ussp.mapper.ExamMapper;
import com.ussp.pojo.Exam;
import com.ussp.pojo.PageResult;
import com.ussp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public PageResult<Exam> page(ExamQueryDTO query) {

        List<Exam> list = examMapper.page(
                query.getKeyword(),
                query.getDirection(),
                query.getStatus(),
                query.getOffset(),
                query.getPageSize()
        );

        Integer total = examMapper.count(
                query.getKeyword(),
                query.getDirection(),
                query.getStatus()
        );

        return new PageResult<>(list, total);
    }

    @Override
    public Exam getById(Integer id) {
        return examMapper.getById(id);
    }

    @Override
    public void add(Exam exam) {
        examMapper.insert(exam);
    }

    @Override
    public void update(Exam exam) {
        examMapper.update(exam);
    }

    @Override
    public void delete(Integer id) {
        examMapper.delete(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        examMapper.updateStatus(id, status);
    }

}
