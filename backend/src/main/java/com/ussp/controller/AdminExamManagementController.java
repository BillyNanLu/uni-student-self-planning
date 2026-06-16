package com.ussp.controller;

import com.ussp.dto.ExamQueryDTO;
import com.ussp.pojo.Exam;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.service.ExamService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/exams")
public class AdminExamManagementController {

    @Resource
    private ExamService examService;

    /** 分页查询 */
    @GetMapping
    public Result<PageResult<Exam>> page(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer direction,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ExamQueryDTO query = new ExamQueryDTO();
        query.setKeyword(keyword);
        query.setDirection(direction);
        query.setStatus(status);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        return Result.success(examService.page(query));
    }

    /** 查看详情 */
    @GetMapping("/{id}")
    public Result<Exam> getById(@PathVariable Integer id) {
        return Result.success(examService.getById(id));
    }

    /** 添加 */
    @PostMapping
    public Result<?> add(@RequestBody Exam exam) {
        examService.add(exam);
        return Result.success();
    }

    /** 修改 */
    @PutMapping
    public Result<?> update(@RequestBody Exam exam) {
        examService.update(exam);
        return Result.success();
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        examService.delete(id);
        return Result.success();
    }

    /** 修改状态 */
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        examService.updateStatus(id, body.get("status"));
        return Result.success();
    }
}