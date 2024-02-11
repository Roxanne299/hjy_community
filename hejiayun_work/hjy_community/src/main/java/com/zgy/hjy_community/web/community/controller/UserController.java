package com.zgy.hjy_community.web.community.controller;

import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.community.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/16 15:06
 * @description UserController
 */
@RestController
public class UserController {
    @RequestMapping("/testUser")
    public BaseResponse login(@Validated User user, BindingResult result){
        int i = 1/0;
        List<FieldError> fieldErrors = result.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            BaseResponse response = new BaseResponse<>();
            response.setMessage(fieldErrors.get(0).getDefaultMessage());
            response.setCode(500);
            return response;
        }
        return BaseResponse.success(user);
    }
}
