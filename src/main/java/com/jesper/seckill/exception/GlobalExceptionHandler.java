package com.jesper.seckill.exception;

import com.jesper.seckill.result.CodeMsg;
import com.jesper.seckill.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Result<String> handleGlobalException(GlobalException e) {
        log.error("Global exception: {}", e.getMessage());
        return Result.error(e.getCodeMsg());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e) {
        log.error("Bind exception: {}", e.getMessage());
        List<FieldError> errors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : errors) {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(sb.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method argument not valid exception: {}", e.getMessage());
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : errors) {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(sb.toString()));
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
