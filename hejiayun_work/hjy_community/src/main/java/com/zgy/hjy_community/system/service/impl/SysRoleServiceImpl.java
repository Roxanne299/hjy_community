package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.mapper.SysRoleMapper;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/6 22:35
 * @description SysRoleServiceImpl
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper mapper;
    @Override
    public List<String> getRolesByUserId(Long userId) {
        return mapper.getRolesByUserId(userId);
    }
}
