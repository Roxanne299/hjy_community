package com.zgy.easypoi_web.service;

import com.zgy.easypoi_web.pojo.Cources;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/23 19:18
 * @description MyService
 */
public interface MyService {
    List<Cources> selectCourceList();
    void AddCourcesList(List<Cources> cources);
}
