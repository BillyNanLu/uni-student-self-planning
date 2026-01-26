package com.ussp.service.impl;

import com.ussp.mapper.SubjectMapper;
import com.ussp.pojo.Subject;
import com.ussp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> getAll() {
        return subjectMapper.findAll();
    }

    @Override
    public void updateSubject(Subject subject) {
        int rows = subjectMapper.updateSubject(subject);
        if (rows == 0) {
            throw new RuntimeException("学科信息更新失败");
        }
    }
}
