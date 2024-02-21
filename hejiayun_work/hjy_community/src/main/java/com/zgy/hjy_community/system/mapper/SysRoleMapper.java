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

    List<Integer> selectRoleListByUserId(Long userId);

    /**
     * 根据用户ID 查询角色权限
     * @param userId
     * @return: java.util.List<java.lang.String>
     */
    public List<String> selectRolePermissionByUserId(Long userId);

    /**
     * 根据条件分页查询角色数据
     *
     * @param sysRole 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(SysRole sysRole);


    /**
     * 根据角色id查询角色权限
     */
    SysRole selectRoleById(Long roleId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

    /**
     * 根据用户ID查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserName(String userName);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    public SysRole checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    public SysRole checkRoleKeyUnique(String roleKey);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRole role);

    /**
     * 新增角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);
}
