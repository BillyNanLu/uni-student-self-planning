package com.ussp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果封装
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private Integer code;   // 业务状态码
    private String message; // 提示信息
    private T data;         // 响应数据

    /**
     * 成功（带数据）
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功（无数据）
     */
    public static <E> Result<E> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 失败（自定义提示信息）
     */
    public static <E> Result<E> error(String message) {
        return new Result<>(ResultCode.ERROR.getCode(), message, null);
    }

    /**
     * 失败（使用预定义错误码）
     */
    public static <E> Result<E> error(ResultCode code) {
        return new Result<>(code.getCode(), code.getMessage(), null);
    }
}