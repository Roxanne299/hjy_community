package com.zgy.hjy_community.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.system.service.TokenService;
import com.zgy.hjy_community.system.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author roxanne_waar
 * @date 2024/2/12 10:23
 * @description LogOutSuccessHandlerImpl
 */
@Component
public class LogOutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    TokenService tokenService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        tokenService.clearUser();
        ServletUtils.renderString(response,BaseResponse.success("退出登录成功").toString());
    }
}
