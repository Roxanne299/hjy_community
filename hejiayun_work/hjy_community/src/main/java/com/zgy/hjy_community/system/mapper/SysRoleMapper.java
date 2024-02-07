package com.zgy.hjy_community.system.mapper;

import com.zgy.hjy_community.system.domain.entity.SysRole;
import com.zgy.hjy_community.system.domain.entity.SysUser;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/5 21:09
 * @description SysUserMapper.xml
 */
public interface SysRoleMapper {

    List<String> getRolesByUserId(Long userId);
}
