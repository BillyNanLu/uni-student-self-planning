package com.ussp.exception;

import com.ussp.pojo.Result;
import com.ussp.pojo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获所有未处理的异常
     */
    @ExceptionHandler(Exception.class)
    public <T> Result<T> handleException(Exception e) {
        log.error("【系统异常】", e);
        String msg = StringUtils.hasLength(e.getMessage()) ? e.getMessage() : ResultCode.SERVER_ERROR.getMessage();
        return Result.error(msg);
    }

    /**
     * 可以单独捕获 NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public <T> Result<T> handleNullPointer(NullPointerException e) {
        log.error("【空指针异常】", e);
        return Result.error("空指针异常，请检查参数或对象是否为空");
    }

}
