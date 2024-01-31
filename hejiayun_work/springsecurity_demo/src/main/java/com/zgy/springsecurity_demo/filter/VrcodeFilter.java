package com.zgy.springsecurity_demo.filter;

import com.google.code.kaptcha.Constants;
import com.zgy.springsecurity_demo.common.CaptchaNotMatchException;
import com.zgy.springsecurity_demo.config.AuthenticationEntryPointImpl;
import com.zgy.springsecurity_demo.pojo.VrCode;
import com.zgy.springsecurity_demo.utils.RedisCache;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author roxanne_waar
 * @date 2024/1/30 19:07
 * @description VrcodeFilter
 */
@Component
public class VrcodeFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("POST")){
            try {
                validate(request);
            } catch (Exception e) {
                authenticationEntryPoint.commence(request,response, (AuthenticationException) e);
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request) throws Exception {
        String req_vr_code = request.getParameter("vrcode");
        req_vr_code = req_vr_code.trim();

        //获取session中的正确验证码
        String tr_code = redisCache.getCacheObject("CAPTCHA_CODE_KEY:" + request.getParameter("uuid"));

        //不管是否成功我们都需要清空session
        if(tr_code != null){
            redisCache.deleteObject("CAPTCHA_CODE_KEY:" + request.getParameter("uuid"));
        }



        if(req_vr_code == null){
            throw new CaptchaNotMatchException("请刷新验证码");
        }
        //校验
        if(StringUtils.isEmpty(req_vr_code)){
            throw new CaptchaNotMatchException("验证码不能为空");
        }
    }
}
