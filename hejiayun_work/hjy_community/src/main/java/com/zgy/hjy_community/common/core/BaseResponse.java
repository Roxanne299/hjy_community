package com.zgy.hjy_community.common.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author roxanne_waar
 * @date 2024/1/16 14:42
 * @description 响应结果封装对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 2992882509925211843L;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应结果描述
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }
    /**
     * 失败响应方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(T data){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.ERROR.getCode());
        response.setMessage(ResultCode.ERROR.getMessage());
        response.setData(data);
        return response;
    }
    /**
     * 失败响应方法
     * @param message
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(String message){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.ERROR.getCode());
        response.setMessage(message);
        return response;
    }



}
