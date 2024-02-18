package com.zgy.hjy_community.web.system;

import com.github.pagehelper.PageInfo;
import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.controller.BaseController;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.core.page.PageResult;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.entity.SysPost;
import com.zgy.hjy_community.system.domain.entity.SysRole;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;
import com.zgy.hjy_community.system.service.*;
import com.zgy.hjy_community.system.service.impl.SysPermissionServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/5 23:08
 * @description HjySystemUserController
 */
@RestController
public class HjySystemUserController extends BaseController {

    @Autowired
    UserLoginService loginService;
    @Autowired
    SysPermissionService permissionService;

    @Autowired
    TokenService tokenService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService roleService;

    @Autowired
    SysPostService postService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;



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
    @GetMapping("/system/user/list")
    public PageResult getUserList(SysUser sysUser){
        startPage();
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        PageResult pageResult = new PageResult(pageInfo.getTotal(),pageInfo.getList(),200,"查询成功！");
        return pageResult;
    }
    @GetMapping(value = {"/system/user/{userId}","/system/user/"})
    public Map getUserInfo(@PathVariable(required = false) Long userId){
        ChainedMap chainedMap = ChainedMap.create().set("code", 200).set("msg", "操作成功");;
        List<SysRole> all_roles = roleService.selectRoleAll();
        List<SysPost> all_posts = postService.selectPostAll();

        //根据用户角色封装相关role
        if(SysUser.isAdmin(userId)){
            chainedMap.set("roles",all_roles);
        }
        else {
            List<SysRole> collect = all_roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
            chainedMap.set("roles",collect);
        }
        chainedMap.set("posts",all_posts);
        //如果是修改操作 需要封装用户自身的角色和岗位
        if(!Objects.isNull(userId)){
            List<Integer> user_posts = postService.selectPostListByUserId(userId);
            List<Integer> user_roles = roleService.selectRoleListByUserId(userId);
            SysUser user = sysUserService.selectUserById(userId);
            chainedMap.set("data",user).set("postIds",user_posts).set("roleIds",user_roles);

        }

        return chainedMap;
    }
    @PostMapping("/system/user")
    public BaseResponse addUser(@RequestBody SysUser user){
        if(!Strings.isBlank(user.getUserName()) && sysUserService.checkUserNameUnique(user.getUserName()) == UserConstants.NOT_UNIQUE){
            BaseResponse.error("用户名已经存在，请更换用户名！");
        }
        if(!Strings.isBlank(user.getEmail()) && sysUserService.checkEmailUnique(user) == UserConstants.NOT_UNIQUE){
            BaseResponse.error("邮箱已被注册过，请更换邮箱！");
        }
        if(!Strings.isBlank(user.getPhonenumber()) && sysUserService.checkPhoneUnique(user) == UserConstants.NOT_UNIQUE){
            BaseResponse.error("手机已被注册过，请更换手机号！");
        }
        user.setCreateBy(TokenUtils.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        int rows = sysUserService.insertUser(user);
        return BaseResponse.success(rows);
    }

    @PutMapping("/system/user")
    public BaseResponse updateUser(@RequestBody SysUser user){
        //校验用户是否允许操作
        sysUserService.checkUserAllowed(user);

        if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(user))){
            return BaseResponse.error("修改用户 '" + user.getUserName() + "'失败,手机号码已经存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(user))){
            return BaseResponse.error("修改用户 '" + user.getUserName() + "'失败,邮箱账号已经存在");
        }
        user.setUpdateBy(TokenUtils.getUsername());
        int rows = sysUserService.updateUser(user);
        return BaseResponse.success(rows);
    }
    @DeleteMapping("/system/user/{userIds}")
    public BaseResponse updateUser(@PathVariable Long[] userIds){
        return BaseResponse.success(sysUserService.deleteUserByIds(userIds));
    }
    @PutMapping("/system/user/resetPwd")
    public BaseResponse resetUserPwd(@RequestBody  SysUser sysUser){
        sysUserService.checkUserAllowed(sysUser);
        sysUser.setUpdateBy(TokenUtils.getUsername());
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        return BaseResponse.success(sysUserService.resetPwd(sysUser));
    }
    @PutMapping("/system/user/changeStatus")
    public BaseResponse setUserStatus(SysUser sysUser){
        sysUserService.checkUserAllowed(sysUser);
        sysUser.setUpdateBy(TokenUtils.getUsername());
        return BaseResponse.success(sysUserService.updateUserStatus(sysUser));
    }






}
