package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.dto.SysAreaDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/18 15:03
 * @description SysAreaService
 */

public interface SysAreaService {
    /**
     * 获取地区三级联动 树形结构
     * @return
     */
    List<SysAreaDto> findAreaAsTree();


}
