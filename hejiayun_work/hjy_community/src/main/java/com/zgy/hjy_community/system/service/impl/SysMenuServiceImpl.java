package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.exception.AuthenticationExceptionImpl;
import com.zgy.hjy_community.system.domain.dto.SysMenuDto;
import com.zgy.hjy_community.system.domain.dto.SysMenuMeta;
import com.zgy.hjy_community.system.domain.entity.SysMenu;
import com.zgy.hjy_community.system.mapper.SysMenuMapper;
import com.zgy.hjy_community.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author roxanne_waar
 * @date 2024/2/7 11:43
 * @description SysMenuServiceImpl
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;


    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        return menuMapper.getPermissionsBYUserId(userId);
    }

    @Override
    public List<SysMenuDto> getAdminMenusAsTree() {
        List<SysMenu> allMenu = menuMapper.getAllMenu();
        List<SysMenuDto> tree = allMenu.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    List<SysMenuDto> subTree = getChildById(menu.getMenuId(), allMenu);
                    if(!subTree.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                        menuDto.setAlwaysShow(true);       //下面有子路由
                        menuDto.setRedirect("noRedirect"); //在导航栏中不可点击
                        menuDto.setChildren(subTree);
                    }else {
                        menuDto.setChildren(new ArrayList<>());
                    }
                    return menuDto;
                }).collect(Collectors.toList());
        return tree;
    }

    @Override
    public List<SysMenuDto> getMenusAsTreeById(Long userId) {
        List<SysMenu> allMenu = menuMapper.getMenuById(userId);
        List<SysMenuDto> tree = allMenu.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    List<SysMenuDto> subTree = getChildById(menu.getMenuId(), allMenu);
                    if(!subTree.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                        menuDto.setAlwaysShow(true);       //下面有子路由
                        menuDto.setRedirect("noRedirect"); //在导航栏中不可点击
                        menuDto.setChildren(subTree);
                    }else {
                        menuDto.setChildren(new ArrayList<>());
                    }
                    return menuDto;
                }).collect(Collectors.toList());
        return tree;
    }

    private SysMenuDto transfer(SysMenu sysMenu) {
        SysMenuDto menuDto = new SysMenuDto();
        menuDto.setName(getRouteName(sysMenu));
        menuDto.setPath(getRoutePath(sysMenu));
        menuDto.setHidden(sysMenu.getVisible().equals("1"));
        menuDto.setComponent(getComponent(sysMenu));
        menuDto.setMeta(new SysMenuMeta(sysMenu.getMenuName(), sysMenu.getIcon(), sysMenu.getIsCache() == 1));
        return menuDto;
    }



    private List<SysMenuDto> getChildById(Long parentId, List<SysMenu> list) {
        List<SysMenuDto> subTrees = list.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    List<SysMenuDto> subTree = getChildById(menu.getMenuId(), list);
                    if(!subTree.isEmpty() && subTree.size() > 1 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                        menuDto.setAlwaysShow(true);       //下面有子路由
                        menuDto.setRedirect("noRedirect"); //在导航栏中不可点击
                        menuDto.setChildren(subTree);
                    }else {
                        menuDto.setChildren(new ArrayList<>());
                    }
                    return menuDto;
                }).collect(Collectors.toList());
        return subTrees;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return: 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = org.apache.commons.lang3.StringUtils.capitalize(menu.getPath());
        return routerName;
    }
    /**
     * 获取路由地址
     *
     * @param menu
     * @return
     */
    public String getRoutePath(SysMenu menu) {
        String routerPath = menu.getPath();
        //非外链 并且是一级目录,菜单类型为 M(目录)
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame().toString())) {
            routerPath = "/" + menu.getPath();
        }

        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu
     * @return
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (!StringUtils.isEmpty(menu.getComponent())) {
            component = menu.getComponent();
        } else if (menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
            component = UserConstants.PARENT_VIEW;
        }

        return component;
    }
}
