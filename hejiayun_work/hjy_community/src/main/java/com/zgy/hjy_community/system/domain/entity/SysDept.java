package com.zgy.hjy_community.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zgy.hjy_community.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 部门表(SysDept)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dept")
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 423701712164411477L;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    private List<SysDept> children;


}

