package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.entity.SysDictData;
import com.zgy.hjy_community.system.domain.entity.SysDictType;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:23
 * @description SysDictService
 */
public interface SysDictDataService {
    List<SysDictData> getDictDataByType(String dictType);
}
