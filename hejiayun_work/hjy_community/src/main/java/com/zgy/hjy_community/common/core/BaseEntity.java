package com.zgy.hjy_community.common.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/16 14:05
 * @description 所有Entity的基类，包含一系列公共字段
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -5280264346418686799L;

    /**
     * 搜索值字段
     * 数据库中不包含的字段
     */
    @TableField(exist = false)
    private String serachValue;

    /**
     * 创建者字段
     * 只在插入时填充
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间字段
     * 只在插入式填充
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间字段
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注字段
     */
    private String remark;

    /**
     * 请求参数
     * 不要求数据库字段
     */
    @TableField(exist = false)
    private Map<String, Object> params;
}
