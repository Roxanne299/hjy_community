package com.zgy.hjy_community.system.mapper;

import com.zgy.hjy_community.system.domain.entity.SysDictData;
import com.zgy.hjy_community.system.domain.entity.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:24
 * @description SysDictTypeMapper.xml
 */
public interface SysDictDataMapper {
    List<SysDictData> selectDictDataByType(String SysDicType);
    int updateDictDataType(@Param("oldDictType") String oldDictType,@Param("newDictType") String newDictType);
}
