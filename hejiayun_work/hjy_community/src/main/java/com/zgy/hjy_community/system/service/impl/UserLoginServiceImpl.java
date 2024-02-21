package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.core.BaseException;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.common.utils.UUIDUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;
import com.zgy.hjy_community.system.service.UserLoginService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author roxanne_waar
 * @date 2024/2/5 21:20
 * @description UserLoginService
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public String login(LoginUserVo user) {

        String uuid = user.getUuid();
        // 验证码校验
        String code = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + uuid);
        if(!Strings.hasText(code) || !Strings.hasText(user.getCode())||!code.equals(user.getCode())){
            throw new BaseException(500,"验证码错误");
        }

        //用户名密码验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new BaseException(500,"用户名或者密码错误");
        }

        //将验证后的用户信息加入到redis
        LoginUserDto loginUserDto = (LoginUserDto) authenticate.getPrincipal();
        String token_uuid = UUIDUtils.getSimpleUUID();
        String token = TokenUtils.createToken(token_uuid);
        redisCache.setCacheObject(Constants.LOGIN_TOKEN_KEY + token_uuid, loginUserDto,20, TimeUnit.MINUTES);

        return token;
    }


}
