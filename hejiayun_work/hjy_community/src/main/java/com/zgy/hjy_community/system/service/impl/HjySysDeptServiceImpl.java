package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.mapper.SysDeptMapper;
import com.zgy.hjy_community.system.service.HjySysDeptService;
import com.zgy.hjy_community.system.domain.entity.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:24
 * @description HjySysDeptServiceImpl
 */
@Service
public class HjySysDeptServiceImpl implements HjySysDeptService {
    @Autowired
    SysDeptMapper mapper;
    @Override
    public List<SysDept> getSysDeptList() {

        return mapper.getDeptList();
    }
}
