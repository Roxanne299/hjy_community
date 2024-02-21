package com.zgy.hjy_community.framework.security.filter;

import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author roxanne_waar
 * @date 2024/2/5 22:52
 * @description SecurityFilter
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    public static final String HEADERS = "Authorization";
    @Autowired
    RedisCache redisCache;

    @Autowired
    TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HEADERS);
        if(!Strings.hasText(token)){
            filterChain.doFilter(request,response);
            return ;
        }

        if(token.startsWith("Bearer ")){
            token = token.substring("Bearer ".length() - 1);
        }
        String uuid = (String) TokenUtils.parseToken(token).get("uuid");
        LoginUserDto loginUserDto = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + uuid);
        if(Objects.isNull(loginUserDto)){
            filterChain.doFilter(request,response);
            return;
        }

        // 登录成功 刷新时间
        tokenService.refreshToken(loginUserDto);

        //将认证信息加入上下文
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
