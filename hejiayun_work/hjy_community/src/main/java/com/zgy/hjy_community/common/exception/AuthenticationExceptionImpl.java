package com.zgy.hjy_community.common.exception;

import com.alibaba.fastjson.JSON;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author roxanne_waar
 * @date 2024/2/6 10:41
 * @description spring security 统一认证失败异常处理
 */
@Component
@Slf4j
public class AuthenticationExceptionImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map map = new HashMap<String ,Object>();
        log.error("认证用户失败： {}",authException.getMessage());
        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.error("用户认证失败")));
    }
}
