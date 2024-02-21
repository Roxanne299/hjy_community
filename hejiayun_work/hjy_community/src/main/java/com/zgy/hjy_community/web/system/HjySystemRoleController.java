package com.zgy.hjy_community.web.system;

import com.github.pagehelper.PageInfo;
import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.controller.BaseController;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.core.page.PageResult;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.entity.SysMenu;
import com.zgy.hjy_community.system.domain.entity.SysRole;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.SysPermissionService;
import com.zgy.hjy_community.system.service.SysRoleService;
import com.zgy.hjy_community.system.service.TokenService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
@RequestMapping("/system/role")
public class HjySystemRoleController extends BaseController {

    @Autowired
    SysRoleService roleService;

    @Autowired
    SysPermissionService permissionService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisCache redisCache;

    @GetMapping("/list")
    public PageResult getRoleList(SysRole role){
        startPage();
        List<SysRole> roles =  roleService.selectRoleList(role);
        return getData(roles);
    }
    @GetMapping("/{roleId}")
    public BaseResponse getRoleInfo(@PathVariable Long roleId){
        SysRole role =  roleService.getRoleInfo(roleId);
        return BaseResponse.success(role);
    }
    @PostMapping
    public BaseResponse addRole(@RequestBody SysRole sysRole){
        if(roleService.checkRoleKeyUnique(sysRole) == UserConstants.NOT_UNIQUE){
            return BaseResponse.error("角色权限已经存在，不能添加！");
        }
        if(roleService.checkRoleNameUnique(sysRole) == UserConstants.NOT_UNIQUE){
            return BaseResponse.error("角色名称已经存在，不能添加！");
        }
        sysRole.setCreateBy(TokenUtils.getUsername());
        roleService.insertRole(sysRole);
        return BaseResponse.success("添加成功");
    }
    @PutMapping
    public BaseResponse updateRole(@RequestBody SysRole sysRole){
        if(!Objects.isNull(sysRole.getRoleId()) && sysRole.isAdmin()){
           BaseResponse.error("不能操作超级管理员！");
        }
        if(roleService.checkRoleKeyUnique(sysRole) == UserConstants.NOT_UNIQUE){
            return BaseResponse.error("角色权限已经存在！");
        }
        if(roleService.checkRoleNameUnique(sysRole) == UserConstants.NOT_UNIQUE){
            return BaseResponse.error("角色名称已经存在！");
        }
        sysRole.setUpdateBy(TokenUtils.getUsername());
        roleService.updateRole(sysRole);


        //更新当前用户信息
        LoginUserDto userDetails = TokenUtils.getUserDetails();
        userDetails.setPerms(permissionService.getMenuPermission(userDetails.getUser().getUserId()).stream().collect(Collectors.toList()));
        userDetails.setRoles(permissionService.getRolePermission(userDetails.getUser().getUserId()).stream().collect(Collectors.toList()));

        //加到上下文
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,null,null));
        //刷新redis
        tokenService.refreshToken(userDetails);

        return BaseResponse.success("更新成功");
    }


    @DeleteMapping("{roleIds}")
    public BaseResponse deleteRoles(@PathVariable Long[] roleIds){
        roleService.deleteRoleByIds(roleIds);
        return BaseResponse.success("删除成功");
    }
    @PutMapping("/changeStatus")
    public BaseResponse deleteRoles(SysRole sysRole){
        if(!Objects.isNull(sysRole.getRoleId()) && sysRole.isAdmin()){
            BaseResponse.error("不能操作超级管理员！");
        }
        sysRole.setUpdateBy(TokenUtils.getUsername());
        roleService.updateRoleStatus(sysRole);
        return BaseResponse.success("更改成功");
    }






}
