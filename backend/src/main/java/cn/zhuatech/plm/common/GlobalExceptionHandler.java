/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.common;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class) ResponseEntity<ApiResponse<Void>> business(BusinessException e){return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e){var error=e.getBindingResult().getFieldError();return ResponseEntity.badRequest().body(ApiResponse.fail(error==null?"请求参数不正确":error.getDefaultMessage()));}
}
