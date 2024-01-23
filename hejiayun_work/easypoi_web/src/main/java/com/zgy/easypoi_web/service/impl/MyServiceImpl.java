package com.zgy.easypoi_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.zgy.easypoi_web.mapper.Mymapper;
import com.zgy.easypoi_web.pojo.Cources;
import com.zgy.easypoi_web.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/23 19:19
 * @description MyServiceImpl
 */
@Service
public class MyServiceImpl implements MyService {
    @Autowired
    Mymapper mymapper;
    @Override
    public List<Cources> selectCourceList() {

        return mymapper.selectList(new QueryWrapper<>());
    }

    @Override
    public void AddCourcesList(List<Cources> cources) {
        for (Cources cource : cources) {
            mymapper.insert(cource);
        }
    }


}
