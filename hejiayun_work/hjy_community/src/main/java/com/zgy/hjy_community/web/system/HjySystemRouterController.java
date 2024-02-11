package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.dto.SysMenuDto;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;
import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.SysPermissionService;
import com.zgy.hjy_community.system.service.TokenService;
import com.zgy.hjy_community.system.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/5 23:08
 * @description HjySystemUserController
 */
@RestController
public class HjySystemRouterController {
    @Autowired
    SysMenuService menuService;

    @GetMapping("/getRouters")
    public BaseResponse getRouters(){
        LoginUserDto user = TokenUtils.getUserDetails();
        List<SysMenuDto> menuTree = null;
        if(SysUser.isAdmin(user.getUser().getUserId())){
            menuTree = menuService.getAdminMenusAsTree();
        }
        else {
            menuTree = menuService.getMenusAsTreeById(user.getUser().getUserId());
        }
        return BaseResponse.success(menuTree);
    }
}
