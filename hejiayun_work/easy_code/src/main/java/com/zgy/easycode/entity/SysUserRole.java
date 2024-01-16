package com.zgy.easycode.entity;

import java.io.Serializable;

/**
 * 用户和角色关联表(SysUserRole)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 613352686026120830L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}

