package com.zgy.springsecurity_demo.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author roxanne_waar
 * @date 2024/1/30 18:48
 * @description VrCode
 */
public class VrCode implements Serializable {

    private static final long serialVersionUID = 6333481564965869971L;
    private String code; //验证字符
    private LocalDateTime expireTime; //过期时间

    public VrCode(String code, int expireTime) {
        this.code = code;
        //返回指定的过期时间
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public VrCode(String code) {
        //默认验证码 60秒后过期
        this(code, 60);
    }

    //是否过期
    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public String getCode() {
        return code;
    }
}
