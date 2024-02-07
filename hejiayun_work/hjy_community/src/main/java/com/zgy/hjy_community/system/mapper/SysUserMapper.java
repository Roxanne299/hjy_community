package com.zgy.hjy_community.system.mapper;

import com.zgy.hjy_community.system.domain.entity.SysUser;

/**
 * @author roxanne_waar
 * @date 2024/2/5 21:09
 * @description SysUserMapper.xml
 */
public interface SysUserMapper {
    SysUser getUserByName(String userName);

}
