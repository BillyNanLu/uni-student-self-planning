package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.Task;
import com.ussp.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @GetMapping("/list")
    public Result<List<Task>> list(@RequestParam Long userId) {
        return Result.success(taskService.list(userId));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Task task) {
        taskService.add(task);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Task task) {
        taskService.update(task);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> toggle(@PathVariable Long id) {
        taskService.toggleStatus(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return Result.success();
    }
}
