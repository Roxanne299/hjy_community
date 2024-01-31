package com.zgy.springsecurity_demo.config;

import com.alibaba.fastjson.JSON;
import com.zgy.springsecurity_demo.common.BaseResponse;
import com.zgy.springsecurity_demo.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/30 19:23
 * @description AuthenticationEntryPointImpl
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put(authException.getMessage(),authException.getCause());
        BaseResponse baseResponse = BaseResponse.error("认证失败",authException);
        String json = JSON.toJSONString(baseResponse);
        WebUtils.renderString(response,json);
    }
}
