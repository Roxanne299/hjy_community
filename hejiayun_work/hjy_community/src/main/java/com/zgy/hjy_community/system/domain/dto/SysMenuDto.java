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
    private Long menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;
    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
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

