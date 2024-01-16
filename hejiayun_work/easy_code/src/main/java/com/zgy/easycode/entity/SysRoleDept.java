package com.zgy.easycode.entity;

import java.io.Serializable;

/**
 * 角色和部门关联表(SysRoleDept)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
public class SysRoleDept implements Serializable {
    private static final long serialVersionUID = 223649121725386386L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 部门ID
     */
    private Long deptId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

}

