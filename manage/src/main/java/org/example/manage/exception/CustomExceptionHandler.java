package org.example.manage.exception;


import org.example.manage.common.ErrorResponse;
import org.example.manage.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class CustomExceptionHandler {
//
//    // 处理业务异常
//    @ExceptionHandler(BusinessException.class)
//    public Result handleBusinessException(BusinessException ex) {
//        return new Result(ex.getCode(), ex.getMessage());
//    }
//
//    // 处理系统异常
//    @ExceptionHandler(SystemException.class)
//    public Result handleSystemException(SystemException ex) {
//        return new Result(ex.getCode(), ex.getMessage());
//    }
//}