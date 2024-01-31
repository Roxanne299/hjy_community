package com.zgy.springsecurity_demo.filter;

import com.zgy.springsecurity_demo.pojo.LoginUser;
import com.zgy.springsecurity_demo.utils.JwtUtil;
import com.zgy.springsecurity_demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/29 15:22
 * @description SecurityFilter
 */
@Component
public class SecurityFilter implements Filter {

    @Autowired
    RedisCache redisCache;

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String token = request1.getHeader("token");
        if(!StringUtils.hasText(token))
        {
            //放行，之后授权会失败的
            filterChain.doFilter(request,servletResponse);
            //防止回来了继续执行
            return;
        }

        String userId = null;
        try {
            userId = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //获取redis中的消息
        LoginUser user = redisCache.getCacheObject("userId:" + userId);


        if(Objects.isNull(user)){
            throw new RuntimeException("用户没有登录");
        }

        //将用户新保存到SecurityContextHolder,以便后续的访问控制和授权操作使用。
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,servletResponse);

    }
}
