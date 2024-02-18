package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.system.domain.entity.TreeSelect;
import com.zgy.hjy_community.system.mapper.SysDeptMapper;
import com.zgy.hjy_community.system.service.HjySysDeptService;
import com.zgy.hjy_community.system.domain.entity.SysDept;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<SysDept> getSysDeptList(SysDept sysDept) {

        return mapper.getDeptList(sysDept);
    }

    @Override
    public SysDept getSysDeptById(Long deptId) {
        return mapper.getDeptById(deptId);
    }

    @Override
    public int insertDept(SysDept sysDept) {
        return mapper.insertDept(sysDept);
    }

    @Override
    public String checkDeptNameUnique(SysDept sysDept) {
        if(mapper.checkDeptNameUnique(sysDept) > 0) return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int updateDept(SysDept sysDept) {
        Long parentId = sysDept.getParentId();
        SysDept new_parent = mapper.getDeptById(parentId);
        SysDept old = mapper.getDeptById(sysDept.getDeptId());

        if(!Objects.isNull(new_parent) && !Objects.isNull(old)){
            String old_ancestor = old.getAncestors();
            String new_ancestor = new_parent.getAncestors() + "," + new_parent.getDeptId();
            sysDept.setAncestors(new_ancestor);
            updateChild(sysDept,old_ancestor,new_ancestor);
        }
        return  mapper.updateDept(sysDept);
    }

    @Override
    public void updateChild(SysDept parent, String old_ancestor, String new_ancestor) {
        List<SysDept> sysDepts = mapper.selectChildrenDeptById(parent.getDeptId());
        for (SysDept dept : sysDepts) {
            dept.setAncestors(dept.getAncestors().replace(old_ancestor,new_ancestor));
        }
        if(!Objects.isNull(sysDepts)){
            for (SysDept dept : sysDepts) {
                mapper.updateDept(dept);
            }
        }
    }

    @Override
    public boolean hasChildrenDepts(Long deptId) {
        int rows = mapper.hasChildByDeptId(deptId);
        if(rows > 0) return true;
        return false;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        if(mapper.checkDeptExistUser(deptId) > 0) return true;
        return false;
    }

    @Override
    public int deleteDept(Long deptId) {
        return mapper.deleteDeptById(deptId);
    }

    @Override
    public List<TreeSelect> getTreeSelect() {
        List<SysDept> sysDeptList = getSysDeptList(new SysDept());
        List<SysDept> sysDepts = buildDeptTree(sysDeptList);
        List<TreeSelect> collect = sysDepts.stream().map(TreeSelect::new).collect(Collectors.toList());


        return collect;
    }

    public List<SysDept> buildDeptTree(List<SysDept> depts){
        List<SysDept> sysDepts = depts.stream().filter(dept -> dept.getParentId().equals(0L))
                .map(dept->{
                    dept.setChildren(getChildTree(dept,depts));
                    return dept;
                }).collect(Collectors.toList());
        return sysDepts;
    }

    private List<SysDept> getChildTree(SysDept dept,List<SysDept> depts) {
        List<SysDept> collect = depts.stream().filter(d -> d.getParentId().equals(dept.getDeptId()))
                .map(d -> {
                    d.setChildren(getChildTree(d, depts));
                    return d;
                }).collect(Collectors.toList());
        return collect;

    }


}
