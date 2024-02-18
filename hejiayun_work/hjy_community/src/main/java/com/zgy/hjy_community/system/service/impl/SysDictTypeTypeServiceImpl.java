package com.zgy.hjy_community.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.constant.UserConstants;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.system.domain.entity.SysDictData;
import com.zgy.hjy_community.system.domain.entity.SysDictType;
import com.zgy.hjy_community.system.mapper.SysDictDataMapper;
import com.zgy.hjy_community.system.mapper.SysDictTypeMapper;
import com.zgy.hjy_community.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:23
 * @description SysDictServiceImpl
 */
@Service
public class SysDictTypeTypeServiceImpl implements SysDictTypeService {

    @Autowired
    SysDictTypeMapper typeMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    SysDictDataMapper dictDataMapper;
    @Override
    public List<SysDictType> getDictTypeList(SysDictType sysDictType) {
        return typeMapper.selectDictTypeList(sysDictType);
    }

    //清空缓存
    public void clear(){
        Collection<String> keys = redisCache.keys(Constants.SYS_DICT_KEY + "*");
        redisCache.deleteObject(keys);
    }
    @PostConstruct
    public void init(){
        List<SysDictType> sysDictTypes = typeMapper.selectDictTypeAll();
        for (SysDictType type:sysDictTypes) {
            List<SysDictData> sysDictData = dictDataMapper.selectDictDataByType(type.getDictType());
            redisCache.setCacheObject(Constants.SYS_DICT_KEY + type.getDictType(),sysDictData);
        }
    }
    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return typeMapper.selectDictTypeById(dictId);
    }

    @Override
    public int insertDictType(SysDictType sysDictType) {
        int rows = typeMapper.insertDictType(sysDictType);
        if(rows > 0)
            clear();
        return rows;
    }

    @Override
    public String checkDictTypeIsUnique(String dictType) {
        if(typeMapper.checkDictTypeUnique(dictType) == 1) return UserConstants.NOT_UNIQUE;
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int updateDictType(SysDictType sysDictType) {
        Long dictId = sysDictType.getDictId();
        SysDictType old_type = typeMapper.selectDictTypeById(dictId);
        dictDataMapper.updateDictDataType(old_type.getDictType(), sysDictType.getDictType());
        int rows = typeMapper.updateDictType(sysDictType);
        if(rows > 0){
            clear();
        }
        return rows;
    }

    @Override
    public int deleteDictTypeById(Long dictId) {
        return  typeMapper.deleteDictTypeById(dictId);
    }

    @Override
    public int deleteDictTypeByIds(Long[] array) {
        int rows = typeMapper.deleteDictTypeByIds(array);
        if(rows > 0)
            clear();
        return rows;

    }
}
