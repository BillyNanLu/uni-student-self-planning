package com.ussp.service;

import com.ussp.pojo.Task;

import java.util.List;

public interface TaskService {

    List<Task> list(Long userId);

    void add(Task task);

    void update(Task task);

    void toggleStatus(Long id);

    void delete(Long id);
}
