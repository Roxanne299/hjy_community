package com.zgy.hjy_community.common.core;

/**
 * @author roxanne_waar
 * @date 2024/1/16 15:22
 * @description 基础异常类
 */
public class BaseException extends RuntimeException{
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String defaultMessage;

    BaseException(){

    }

    public BaseException(Integer code,String defaultMessage){
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public Integer getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

}
