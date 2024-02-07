package com.zgy.hjy_community.web.common;

import com.zgy.hjy_community.common.core.BaseException;
import com.zgy.hjy_community.common.core.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

/**
 * @author roxanne_waar
 * @date 2024/1/16 15:27
 * @description 全局异常处理类
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BaseException.class)
    public BaseResponse BaseExceptionHandler(BaseException e){
        return BaseResponse.error(e.getMessage());
    }
    @ExceptionHandler(ConnectException.class)
    public BaseResponse connnectExceptionHandler(ConnectException e){
        return BaseResponse.error("检查redis是否没打开");
    }
}
