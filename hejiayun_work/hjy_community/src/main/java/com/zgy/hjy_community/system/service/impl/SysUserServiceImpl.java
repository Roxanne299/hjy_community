package com.zgy.hjy_community.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseException;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.entity.SysUserPost;
import com.zgy.hjy_community.system.domain.entity.SysUserRole;
import com.zgy.hjy_community.system.mapper.SysPostMapper;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.mapper.SysUserPostMapper;
import com.zgy.hjy_community.system.mapper.SysUserRoleMapper;
import com.zgy.hjy_community.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spikeCong
 * @date 2023/5/3
 **/
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }



    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验用户称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (!Objects.isNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (!Objects.isNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (!Objects.isNull(user.getUserId()) && SysUser.isAdmin(user.getUserId()))
        {
            throw new BaseException(500,"不允许操作超级管理员用户");
        }
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {

        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {

        return null;
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        return null;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
//    @Transactional
    public int insertUser(SysUser user) {

        int rows = userMapper.insertUser(user);
        Long userId = userMapper.getUserIdByName(user.getUserName());
        user.setUserId(userId);
        //对岗位用户表进行添加
        if(!Objects.isNull(user.getPostIds())){
            insertUserPost(user);
        }
        //对角色用户表进行添加
        if(!Objects.isNull(user.getRoleIds())){
            insertUserRole(user);
        }
        return rows;
    }

    public void insertUserPost(SysUser sysUser){
        List<SysUserPost> list = new ArrayList<>();
        for (Long postId:sysUser.getPostIds()){
            list.add(new SysUserPost(sysUser.getUserId(),postId));
        }
        if(list.size() > 0){
            userPostMapper.batchUserPost(list);
        }
    }
    public void insertUserRole(SysUser sysUser){
        List<SysUserRole> list = new ArrayList<>();
        for (Long roleId:sysUser.getRoleIds()){
            list.add(new SysUserRole(sysUser.getUserId(),roleId));
        }
        if(list.size() > 0){
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
//    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();

        //删除用户关联角色
        userRoleMapper.deleteUserRoleByUserId(userId);
        if(!Objects.isNull(user.getRoleIds())){
            insertUserRole(user);
        }

        //删除用户关联岗位
        userPostMapper.deleteUserPostByUserId(userId);
        if(!Objects.isNull(user.getPostIds()))
        {
            insertUserPost(user);
        }

        return userMapper.updateUser(user);
    }


    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        return 0;
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] userIds) {
        return userMapper.deleteUserByIds(userIds);
    }


}
