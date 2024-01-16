package com.zgy.easycode.entity;

import java.io.Serializable;

/**
 * 角色和菜单关联表(SysRoleMenu)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 608405016633678675L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}

