package com.camellia.handler;

import com.camellia.util.JsonResult;
import com.camellia.util.ResultCode;
import com.camellia.util.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public JsonResult handler(AccessDeniedException e){
        log.error("权限不足----{}",e.getMessage());
        return ResultTool.fail(ResultCode.NO_PERMISSION);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = ArithmeticException.class)
    public JsonResult handler(ArithmeticException e) {
        log.error("系统内部异常：----------------{}", e.getMessage());
        return ResultTool.fail();
    }
}
