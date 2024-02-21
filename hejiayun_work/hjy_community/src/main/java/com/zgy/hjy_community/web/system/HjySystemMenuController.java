package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.SysMenuDto;
import com.zgy.hjy_community.system.domain.entity.SysMenu;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.entity.TreeSelect;
import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.SysRoleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/19 15:58
 * @description HjySystemMenuController
 */
@RestController
@RequestMapping("/system/menu")
public class HjySystemMenuController {

    @Autowired
    SysMenuService menuService;

    @Autowired
    SysRoleService roleService;

    @GetMapping("/list")
    public BaseResponse getMenuList(SysMenu menu){
        List<SysMenu> menus =  menuService.getMenuList(menu);
        return BaseResponse.success(menus);
    }

    @GetMapping("/{menuId}")
    public BaseResponse getMenuInfo(@PathVariable Long menuId){
        SysMenu menu = menuService.getMenuInfo(menuId);
        return BaseResponse.success(menu);
    }

    @PostMapping
    public BaseResponse addMenu(@RequestBody SysMenu menu){
        if(!Strings.isBlank(menu.getMenuName()) && menuService.checkMenuNameUnique(menu.getMenuName(),menu.getParentId()) == UserConstants.NOT_UNIQUE)
            return BaseResponse.error("菜单名称重复，请更换！");
        if(UserConstants.YES_FRAME.equals(menu.getIsFrame().toString()) && !StringUtils.startsWithIgnoreCase(menu.getPath(), Constants.HTTP) && !StringUtils.startsWithIgnoreCase(menu.getPath(), Constants.HTTPS))
            return BaseResponse.error("外链地址必须以https或者http开头");
        menuService.addMenu(menu);
        return BaseResponse.success("添加成功");

    }
    @PutMapping
    public BaseResponse updateMenu(@RequestBody SysMenu menu){
        if(!Strings.isBlank(menu.getMenuName()) && menuService.checkMenuNameUnique(menu.getMenuName(),menu.getParentId()) == UserConstants.NOT_UNIQUE)
            return BaseResponse.error("菜单名称重复，请更换！");
        if(UserConstants.YES_FRAME.equals(menu.getIsFrame().toString()) && !StringUtils.startsWithIgnoreCase(menu.getPath(), Constants.HTTP) && !StringUtils.startsWithIgnoreCase(menu.getPath(), Constants.HTTPS))
            return BaseResponse.error("外链地址必须以https或者http开头");
        if(menu.getMenuId().equals(menu.getParentId()))
            return BaseResponse.error("父节点不可以是自己");
        menuService.updateMenu(menu);
        return BaseResponse.success("添加成功");

    }

    @DeleteMapping("/{menuId}")
    public BaseResponse deleteMenu(@PathVariable Long menuId){
        if(menuService.hasChildren(menuId))
            return BaseResponse.error("菜单下面含有子节点，不能删除！");
        menuService.deleteMenuById(menuId);
        return BaseResponse.success("删除成功");
    }
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ChainedMap getMenuTreeselect(@PathVariable Long roleId){
        SysUser user = TokenUtils.getUserDetails().getUser();
        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");
        map.set("checkedKeys",menuService.selectMenuListByRoleId(roleId));
        List<SysMenu> menuTreeById = menuService.getMenuTreeById(user.getUserId());
        List<TreeSelect> collect = menuTreeById.stream().map(menu -> new TreeSelect(menu)).collect(Collectors.toList());
        map.set("menus",collect);
        return map;
    }
    @GetMapping("/treeselect")
    public BaseResponse getMenuTreeselect(){
        SysUser user = TokenUtils.getUserDetails().getUser();
        List<SysMenu> menuTreeById = menuService.getMenuTreeById(user.getUserId());
        List<TreeSelect> collect = menuTreeById.stream().map(menu -> new TreeSelect(menu)).collect(Collectors.toList());
        return BaseResponse.success(collect);
    }

}
