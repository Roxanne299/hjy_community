package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.controller.BaseController;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.core.page.PageResult;
import com.zgy.hjy_community.common.exception.AuthenticationExceptionImpl;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.common.utils.ExcelUtils;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.entity.SysDictData;
import com.zgy.hjy_community.system.domain.entity.SysDictType;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.domain.vo.LoginUserVo;
import com.zgy.hjy_community.system.mapper.SysDictDataMapper;
import com.zgy.hjy_community.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/5 23:08
 * @description HjySystemUserController
 */
@RestController
@RequestMapping("/system/dict")
public class HjySystemDistController extends BaseController {

    @Autowired
    SysDictTypeService dictTypeService;

    @Autowired
    SysDictDataService dictDataService;


    @GetMapping("/type/list")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public PageResult getDictList(SysDictType dictType){
        startPage();
        List<SysDictType> dictTypeList = dictTypeService.getDictTypeList(dictType);
        PageResult data = getData(dictTypeList);
        return data;
    }

    @GetMapping("/data/type/{dictType}")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse getDictDataByType(@PathVariable String dictType){
        List<SysDictData> dictDataByType = dictDataService.getDictDataByType(dictType);
        return BaseResponse.success(dictDataByType);
    }

    @GetMapping("/type/{dictId}")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse getDictTypeById(@PathVariable Long dictId){
        SysDictType sysDictType = dictTypeService.selectDictTypeById(dictId);
        return BaseResponse.success(sysDictType);
    }

    @PostMapping("/type")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse addDictType(@RequestBody SysDictType dictType){
        if(!Objects.isNull(dictType.getDictName()) && dictTypeService.checkDictTypeIsUnique(dictType.getDictType()) == UserConstants.NOT_UNIQUE)
            return BaseResponse.error("字典类型已经存在，请修改类型后添加！");
        dictType.setCreateBy(TokenUtils.getUsername());
        return BaseResponse.success(dictTypeService.insertDictType(dictType));
    }
    @PutMapping("/type")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse updateDictType(@RequestBody SysDictType dictType){
        if(!Objects.isNull(dictType.getDictName()) && dictTypeService.checkDictTypeIsUnique(dictType.getDictType()) == UserConstants.NOT_UNIQUE)
            return BaseResponse.error("字典类型已经存在，请修改类型后修改！");
        dictType.setUpdateBy(TokenUtils.getUsername());
        return BaseResponse.success(dictTypeService.updateDictType(dictType));
    }
    @DeleteMapping("/type/{dictIds}")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse deleteDictType(@PathVariable Long[] dictIds){

        return BaseResponse.success(dictTypeService.deleteDictTypeByIds(dictIds));
    }
    @GetMapping("/type/export")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse exportDictType(SysDictType sysDictType) throws Exception {
        startPage();
        List<SysDictType> dictTypeList = dictTypeService.getDictTypeList(sysDictType);
        ExcelUtils.exportExcel(ServletUtils.getResponse(),dictTypeList,SysDictType.class,"字典类型","sheet1");
        return BaseResponse.success("导出成功");
    }
    @DeleteMapping("/type/clearCache")
    @PreAuthorize("@ex.hasPerms('system:dict:list')")
    public BaseResponse deleteRedisCache(){
        dictTypeService.clear();
        return BaseResponse.success("清除成功");
    }
}
