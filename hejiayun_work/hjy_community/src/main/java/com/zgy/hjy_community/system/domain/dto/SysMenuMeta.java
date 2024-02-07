package com.zgy.hjy_community.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author roxanne_waar
 * @date 2024/2/7 23:51
 * @description 菜单component
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuMeta {
    /**
     * 菜单名称
     */
    private String title;
    /**
     *菜单图标
     */
    private String icon;
    private boolean noCache;
}
