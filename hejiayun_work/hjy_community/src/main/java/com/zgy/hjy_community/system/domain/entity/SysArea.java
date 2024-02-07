package com.zgy.hjy_community.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 区域表(SysArea)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_area")
public class SysArea implements Serializable {
    private static final long serialVersionUID = -34298201339804768L;
    /**
     * 唯一主键
     */
    private Integer id;
    /**
     * 城市编码
     */
    private Integer code;
    /**
     * 城市名称
     */
    private String name;
    /**
     * 城市父ID
     */
    private Integer parentid;
    /**
     * 城市等级(0:省,1:市,2:县)
     */
    private Integer level;
    /**
     * 功能类型(1:角色认证地点开通;0:未开通)
     */
    private Integer type;
    /**
     * 服务状态
     */
    private Integer servicestate;
    /**
     * 逻辑删除 0正常 1 删除
     */
    private Integer deleteFlag;
    /**
     * 地区范围
     */
    private Integer region;

}

