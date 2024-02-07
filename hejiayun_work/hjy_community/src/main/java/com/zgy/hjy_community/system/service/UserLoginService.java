package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;

/**
 * @author roxanne_waar
 * @date 2024/2/5 22:22
 * @description UserLoginService
 */
public interface UserLoginService {
    public String login(LoginUserVo user);
}
