package com.zgy.hjy_community.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgy.hjy_community.system.domain.entity.HjySystemDept;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:23
 * @description HjySysDeptMapper
 */
public interface HjySystemDeptMapper extends BaseMapper<HjySystemDept> {
    List<HjySystemDept> getDeptList();
}
