package com.zgy.hjy_community.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgy.hjy_community.system.domain.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:23
 * @description HjySysDeptMapper
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    List<SysDept> getDeptList(SysDept dept);

    SysDept getDeptById(Long deptId);

    int insertDept(SysDept sysDept);
    int checkDeptNameUnique(SysDept sysDept);

    int updateDept(SysDept sysDept);

    int hasChildByDeptId(@Param("deptId") Long deptId);

    List<SysDept> selectChildrenDeptById(@Param("deptId") Long deptId);
    int checkDeptExistUser(@Param("deptId") Long deptId);

    int deleteDeptById(@Param("deptId") Long deptId);

}
