package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseException;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.system.domain.entity.SysRole;
import com.zgy.hjy_community.system.domain.entity.SysRoleMenu;
import com.zgy.hjy_community.system.mapper.SysRoleMapper;
import com.zgy.hjy_community.system.mapper.SysRoleMenuMapper;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.mapper.SysUserRoleMapper;
import com.zgy.hjy_community.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/2/6 22:35
 * @description SysRoleServiceImpl
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper mapper;

    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;
    @Override
    public List<String> getRolesByUserId(Long userId) {
        return mapper.getRolesByUserId(userId);
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return mapper.selectRoleList(new SysRole());
    }

    @Override
    public List<Integer> selectRoleListByUserId(Long userId) {
        return  mapper.selectRoleListByUserId(userId);
    }

    @Override
    public List<SysRole> selectRoleList(SysRole sysRole) {
        return mapper.selectRoleList(sysRole);
    }

    @Override
    public SysRole getRoleInfo(Long roleId) {
        return mapper.selectRoleById(roleId);
    }

    @Override
    @Transactional
    public void insertRole(SysRole sysRole) {
        mapper.insertRole(sysRole);
        insertRoleMenu(sysRole);
    }

    @Override
    public String checkRoleNameUnique(SysRole sysRole) {
        SysRole sysRole1 = mapper.selectRoleById(sysRole.getRoleId());
        if(sysRole1.getRoleName().equals(sysRole.getRoleName())) return UserConstants.UNIQUE;
        if(!Objects.isNull(mapper.checkRoleNameUnique(sysRole.getRoleName()))) return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(SysRole sysRole) {
        SysRole sysRole1 = mapper.selectRoleById(sysRole.getRoleId());
        if(sysRole1.getRoleKey().equals(sysRole.getRoleKey())) return UserConstants.UNIQUE;
        if(!Objects.isNull(mapper.checkRoleKeyUnique(sysRole.getRoleKey()))) return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public void updateRole(SysRole role) {
        mapper.updateRole(role);
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        insertRoleMenu(role);
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId:roleIds) {
            if(SysRole.isAdmin(roleId))
                throw new BaseException(500,"不能操作超级管理员");
            if(userRoleMapper.countUserRoleByRoleId(roleId) > 0)
                throw new BaseException(500,"该角色分配过用户，不能删除");
        }
        return mapper.deleteRoleByIds(roleIds);
    }

    @Override
    public void updateRoleStatus(SysRole sysRole) {
        mapper.updateRole(sysRole);
    }

    public void insertRoleMenu(SysRole sysRole){
        Long roleId = sysRole.getRoleId();
        Long[] menuIds = sysRole.getMenuIds();
        List<SysRoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId: menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenuList.add(roleMenu);
        }
        roleMenuMapper.batchRoleMenu(roleMenuList);
    }

}
