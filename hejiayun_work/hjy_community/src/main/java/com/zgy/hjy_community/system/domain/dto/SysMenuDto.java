package com.zgy.hjy_community.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/7 23:44
 * @description SysMenuDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuDto implements Serializable {
    private static final long serialVersionUID = 1333598680882769447L;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 菜单是否隐藏
     */
    private boolean hidden;
    /**
     * 重定向
     */
    private String redirect;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 是否一直展示
     */
    private boolean alwaysShow;

    /**
     * 元素
     */
    private SysMenuMeta meta;

    /**
     * 子菜单
     */
    private List<SysMenuDto> children;


}

