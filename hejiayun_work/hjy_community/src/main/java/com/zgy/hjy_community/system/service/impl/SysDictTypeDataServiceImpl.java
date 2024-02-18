package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.system.domain.entity.SysDictData;
import com.zgy.hjy_community.system.domain.entity.SysDictType;
import com.zgy.hjy_community.system.mapper.SysDictDataMapper;
import com.zgy.hjy_community.system.mapper.SysDictTypeMapper;
import com.zgy.hjy_community.system.service.SysDictDataService;
import com.zgy.hjy_community.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/2/13 23:23
 * @description SysDictServiceImpl
 */
@Service
public class SysDictTypeDataServiceImpl implements SysDictDataService {
    @Autowired
    SysDictDataMapper dictDataMapper;
    @Autowired
    RedisCache redisCache;

    @Override
    public List<SysDictData> getDictDataByType(String dictType) {
        // 先在缓存中找
        List<SysDictData> dictDatas = redisCache.getCacheObject(Constants.SYS_DICT_KEY + dictType);
        if(!Objects.isNull(dictDatas)){
            return dictDatas;
        }

        List<SysDictData> sysDictData = dictDataMapper.selectDictDataByType(dictType);
        if(!Objects.isNull(sysDictData))
            redisCache.setCacheObject(Constants.SYS_DICT_KEY+dictType,sysDictData);
        return sysDictData;
    }
}
