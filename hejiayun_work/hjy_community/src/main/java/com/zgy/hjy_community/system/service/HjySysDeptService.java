package com.zgy.hjy_community.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.system.domain.entity.SysDept;
import com.zgy.hjy_community.system.domain.entity.TreeSelect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:21
 * @description SysDeptService
 */

public interface HjySysDeptService {
    List<SysDept> getSysDeptList(SysDept  sysDept);

    SysDept getSysDeptById(Long deptId);
    int insertDept(SysDept sysDept);

    String checkDeptNameUnique(SysDept sysDept);

    int updateDept(SysDept sysDept);

    void updateChild(SysDept parent,String old_ancestor,String new_ancestor);

    boolean hasChildrenDepts(Long deptId);
    boolean checkDeptExistUser( Long deptId);

    int deleteDept(Long deptId);

    List<TreeSelect> getTreeSelect();
}
