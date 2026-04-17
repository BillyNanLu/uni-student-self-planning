package com.ussp.service.impl;

import com.ussp.mapper.TaskMapper;
import com.ussp.pojo.Task;
import com.ussp.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public List<Task> list(Long userId) {
        return taskMapper.selectByUserId(userId);
    }

    @Override
    public void add(Task task) {
        task.setStatus(0);
        task.setIsDelete(0);
        taskMapper.insertTask(task);
    }

    @Override
    public void update(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public void toggleStatus(Long id) {
        Task task = taskMapper.selectById(id);
        if (task.getStatus() == 0) {
//            task.setStatus(1);
//            task.setCompleteTime(LocalDateTime.now());
            taskMapper.updateStatus(id, 1, LocalDateTime.now());
        } else {
//            task.setStatus(0);
//            task.setCompleteTime(null);
            taskMapper.updateStatus(id, 0, null);
        }
    }

    @Override
    public void delete(Long id) {
        taskMapper.logicDelete(id);
    }
}
