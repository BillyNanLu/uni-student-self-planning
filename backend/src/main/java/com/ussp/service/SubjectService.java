package com.ussp.service;

import com.ussp.pojo.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAll();

    /**
     * 修改学科信息
     */
    void updateSubject(Subject subject);
}
