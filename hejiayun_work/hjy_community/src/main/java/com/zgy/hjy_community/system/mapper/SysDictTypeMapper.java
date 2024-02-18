package com.zgy.hjy_community.system.mapper;

import com.zgy.hjy_community.system.domain.entity.SysDictType;
import com.zgy.hjy_community.system.service.SysDictTypeService;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:24
 * @description SysDictTypeMapper.xml
 */
public interface SysDictTypeMapper {
    List<SysDictType> selectDictTypeList(SysDictType sysDictType);
    SysDictType selectDictTypeById(Long dictId);

    int insertDictType(SysDictType dictType);
    int checkDictTypeUnique(String  dictName);

    int updateDictType(SysDictType dictType);
    int deleteDictTypeById(Long dictId);

    int deleteDictTypeByIds(Long[] array);
    List<SysDictType> selectDictTypeAll();
}
