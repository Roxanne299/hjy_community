package com.zgy.hjy_community.framework.security.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.entity.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/2/11 0:10
 * @description PermsExpressionService
 */
@Service("ex")
public class PermsExpressionService {
    /** 所有权限的标识 */
    private static final String ALL_PERMISSION = "*:*:*";

    private static final String DELIMITERS = ",";


    /**
     * 验证用户是否具备某权限
     * @param permission    权限字符串
     * @return: boolean     是否拥有权限
     */
    public boolean hasPerms(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }

        LoginUserDto userDetails = TokenUtils.getUserDetails();

        if(Objects.isNull(userDetails) || CollectionUtils.isEmpty(userDetails.getPerms())){
            return false;
        }

        return hasPermissions(userDetails.getPerms(),permission);
    }

    /**
     * 判断是否包含权限
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return: boolean
     */
    private boolean hasPermissions(List<String> permissions, String permission) {

        return permissions.contains(ALL_PERMISSION) || permissions.contains(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     * @param permissions
     * @return: boolean
     */
    public boolean hasAnyPerms(String permissions){
        if(StringUtils.isEmpty(permissions)){
            return false;
        }

        LoginUserDto userDetails = TokenUtils.getUserDetails();

        if(Objects.isNull(userDetails) || CollectionUtils.isEmpty(userDetails.getPerms())){
            return false;
        }

        List<String> authorities = userDetails.getPerms();
        for (String permission : permissions.split(DELIMITERS)) {
            if(permission != null && hasPermissions(authorities,permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     * @param role  角色字符串
     * @return: boolean
     */
    public boolean hasRole(String role){
        if(StringUtils.isEmpty(role)){
            return false;
        }

        LoginUserDto userDetails = TokenUtils.getUserDetails();
        if(Objects.isNull(userDetails) || CollectionUtils.isEmpty(userDetails.getUser().getRoles())){
            return false;
        }
        for (SysRole sysRole : userDetails.getUser().getRoles()) {
            String roleKey = sysRole.getRoleKey();
            if("admin".equals(roleKey) || roleKey.equals(role)){
                return true;
            }
        }
        return false;
    }


    /**
     * 判断用户是否具有以下任意一个角色
     * @param roles  角色字符串,多个角色用逗号分隔
     * @return: boolean
     */
    public boolean hasAnyRole(String roles){

        if(StringUtils.isEmpty(roles)){
            return false;
        }

        LoginUserDto userDetails = TokenUtils.getUserDetails();
        if(Objects.isNull(userDetails) || CollectionUtils.isEmpty(userDetails.getUser().getRoles())){
            return false;
        }
        for (String role : roles.split(DELIMITERS)) {
            if(hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
