package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.entity.SysDictType;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:23
 * @description SysDictService
 */
public interface SysDictTypeService {
    List<SysDictType> getDictTypeList(SysDictType sysDictType);
    SysDictType selectDictTypeById(Long dictId);
    int insertDictType(SysDictType sysDictType);
    String checkDictTypeIsUnique(String dictType);
    int updateDictType(SysDictType sysDictType);
    int deleteDictTypeById(Long dictId);
    int deleteDictTypeByIds(Long[] array);
    void clear();
}
