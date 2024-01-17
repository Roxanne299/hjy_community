package com.zgy.hjy_community.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgy.hjy_community.common.constant.HttpStatus;
import com.zgy.hjy_community.common.core.page.PageResult;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/17 15:51
 * @description 所有Controller的基类，提供一些公共方法
 */
public class BaseController {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 获取分页数并且开启分页
     */
    public void startPage(){
        // 获取分页参数
        Integer pageNum = ServletUtils.getParameterToInt("pageNum");
        Integer pageSize = ServletUtils.getParameterToInt("pageSize");

        // 开启分页
        if(pageSize != null && pageSize != null){
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    public PageResult getData(List<?> list){
        PageResult pageResult = new PageResult();
        // 封装返回分页结果
        PageInfo<?> pageInfo = new PageInfo<>(list);
        pageResult.setCode(HttpStatus.SUCCESS);
        pageResult.setMsg("操作成功");
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(list);
        return pageResult;
    }

}
