package com.zgy.hjy_community.web.controller;

import com.zgy.hjy_community.common.core.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author roxanne_waar
 * @date 2024/1/16 15:27
 * @description 全局异常处理类
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse exceptionHandler(RuntimeException e){
        return BaseResponse.error(e.getMessage());
    }
}
