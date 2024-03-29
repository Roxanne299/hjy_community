package com.zgy.hjy_community.system.service;

import com.microsoft.schemas.office.office.STInsetMode;
import com.zgy.hjy_community.system.domain.entity.SysRole;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/6 22:21
 * @description SysRoleService
 */
public interface SysRoleService {
    List<String> getRolesByUserId(Long userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

    List<Integer> selectRoleListByUserId(Long userId);
    List<SysRole> selectRoleList(SysRole sysRole);

    SysRole getRoleInfo(Long roleId);

    void insertRole(SysRole sysRole);
    String checkRoleNameUnique(SysRole sysRole);

    String checkRoleKeyUnique(SysRole sysRole);
    void updateRole(SysRole role);
    int deleteRoleByIds(Long[] roleIds);
    void updateRoleStatus(SysRole sysRole);

}
