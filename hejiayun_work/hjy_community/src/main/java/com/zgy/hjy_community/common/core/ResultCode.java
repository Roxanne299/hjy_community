package com.zgy.hjy_community.common.core;

/**
 * @author roxanne_waar
 * @date 2024/1/16 14:31
 * @description 自定义返回操作状态的枚举类
 */
public enum ResultCode {
    SUCCESS("200","操作成功"),
    ERROR("500","操作失败");

    /**
     * 自定义状态码
     */
    private String code;
    /**
     *自定义描述
     */
    private String message;

    ResultCode(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

}
