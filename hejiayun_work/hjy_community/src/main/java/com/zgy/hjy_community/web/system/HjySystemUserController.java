package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;
import com.zgy.hjy_community.system.service.*;
import com.zgy.hjy_community.system.service.impl.SysPermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
public class HjySystemUserController {

    @Autowired
    UserLoginService loginService;
    @Autowired
    SysPermissionService permissionService;

    @Autowired
    TokenService tokenService;



    @PostMapping("/login")
    public Map login(@RequestBody  LoginUserVo userVo){
        String token = loginService.login(userVo);
        return ChainedMap.create().set("token",token);
    }
    @GetMapping("/getInfo")
    public Map getinfo(){
        SysUser userInfo = tokenService.getUserInfo();
        Set<String> menus = permissionService.getMenuPermission(userInfo.getUserId());
        Set<String> roles = permissionService.getRolePermission(userInfo.getUserId());
        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");
        map.set("user",userInfo);
        map.set("roles",roles.stream().collect(Collectors.toList()));
        map.set("permissions",menus.stream().collect(Collectors.toList()));
        return map;
    }
}
