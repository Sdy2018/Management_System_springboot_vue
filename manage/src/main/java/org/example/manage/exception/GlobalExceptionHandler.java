package org.example.manage.exception;

import org.example.manage.common.ErrorResponse;
import org.example.manage.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

//     处理 RuntimeException（业务异常）
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?>  handleARuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new HashMap<String, Object>() {{
                    put("msg", e.getMessage());
                }});
    }
    // 处理所有未捕获的异常（系统异常）
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),  // 系统异常返回 5xx
                "系统异常: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),  // 系统异常返回 5xx
                "数据异常: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}




//package org.example.manage.exception;
//
//import org.example.manage.common.ErrorResponse;
//import org.example.manage.common.Result;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@ControllerAdvice // 或 @RestControllerAdvice（直接返回 JSON，无需额外 @ResponseBody）
//public class GlobalExceptionHandler {
//
//    // 处理特定异常（示例：处理除零异常）
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public Result handleARuntimeException(RuntimeException e) {
//        // 记录日志（推荐）
//        // log.error("除零异常", e);
//        return Result.error("计算错误：" + e.getMessage());
//    }
//
//    // 处理其他通用异常（兜底方案）
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result handleException(Exception e) {
//        // 记录日志（推荐）
//        // log.error("系统异常", e);
//        return Result.error("系统繁忙，请稍后重试");
//    }
//}
//

