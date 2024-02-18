package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.entity.TreeSelect;
import com.zgy.hjy_community.system.service.HjySysDeptService;
import com.zgy.hjy_community.system.domain.entity.SysDept;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:47
 * @description HjySystemDeptController
 */
@RestController
@RequestMapping("/system/dept")
public class HjySystemDeptController {
    @Autowired
    HjySysDeptService service;


    @GetMapping("/list")
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse<List<SysDept>> getSystemDeptList(SysDept sysDept){
        return BaseResponse.success(service.getSysDeptList(sysDept));
    }

    @GetMapping("/{deptId}")
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse getDeptById(@PathVariable Long deptId){
        SysDept sysDeptById = service.getSysDeptById(deptId);
        return BaseResponse.success(sysDeptById);
    }
    @GetMapping("/list/exclude/{deptId}")
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse DeptExcludeChild(@PathVariable Long deptId){
        List<SysDept> sysDeptList = service.getSysDeptList(new SysDept());
        Iterator<SysDept> iterator = sysDeptList.iterator();
        while(iterator.hasNext()){
            SysDept next = iterator.next();
            if(next.getDeptId() == deptId || ArrayUtils.contains(next.getAncestors().split(","),deptId+"")){
                iterator.remove();
            }
        }
        return BaseResponse.success(sysDeptList);
    }

    @PostMapping
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse insertDept(@RequestBody SysDept sysDept){
        if(service.checkDeptNameUnique(sysDept) == UserConstants.NOT_UNIQUE)
            return BaseResponse.error("已经存在这个部门，请修改部门名称或者父节点！");
        sysDept.setCreateBy(TokenUtils.getUsername());
        int rows = service.insertDept(sysDept);
        return BaseResponse.success(rows);
    }
    @PutMapping
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse updateDept(@RequestBody SysDept sysDept){
        SysDept sysDeptById = service.getSysDeptById(sysDept.getDeptId());
        if(service.checkDeptNameUnique(sysDept) == UserConstants.NOT_UNIQUE){
            return BaseResponse.error("修改部门已存在，请更改！");
        }
        sysDept.setUpdateBy(TokenUtils.getUsername());
        int rows = service.updateDept(sysDept);
        return BaseResponse.success(rows);
    }

    @DeleteMapping("/{deptId}")
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse deleteDept(@PathVariable Long deptId){
        if(service.hasChildrenDepts(deptId)){
            return BaseResponse.error("存在下属部门，不可以删除！");
        }
        if(service.checkDeptExistUser(deptId)){
            return BaseResponse.error("部门存在员工，不可以删除！");
        }
        int rows = service.deleteDept(deptId);
        return BaseResponse.success(rows);
    }
    @GetMapping("/treeselect")
    @PreAuthorize("@ex.hasPerms('system:dept:list')")
    public BaseResponse treeSelect(){
        List<TreeSelect> treeSelect = service.getTreeSelect();
        return BaseResponse.success(treeSelect);
    }


}
