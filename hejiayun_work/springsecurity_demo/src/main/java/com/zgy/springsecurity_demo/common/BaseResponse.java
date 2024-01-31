package com.zgy.springsecurity_demo.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/29 15:05
 * @description BaseResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 6492046450268487443L;
    private String code;
    private String message;
    private T data;

    public static BaseResponse success(Object data){
        return new BaseResponse("200","操作成功",data);
    }
    public static BaseResponse error(String message){
        return new BaseResponse("500",message,null);
    }
    public static BaseResponse error(String message,Object data){
        return new BaseResponse("500",message,data);
    }
}
