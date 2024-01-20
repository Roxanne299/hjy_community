package com.zgy.hjy_community.web.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgy.hjy_community.common.constant.HttpStatus;
import com.zgy.hjy_community.common.controller.BaseController;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.core.page.PageResult;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;
import com.zgy.hjy_community.community.service.HjyCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/17 15:24
 * @description HjyCommunityController
 */
@RestController
@RequestMapping("/community")
public class HjyCommunityController extends BaseController {

    @Autowired
    private HjyCommunityService service;

    @GetMapping("/list")
    public PageResult list(HjyCommunity community){
        // 开启分页
        startPage();
        List<HjyCommunityDto> hjyCommunityDtos = service.selectHjyCommunityList(community);
        // 封装返回分页结果
        PageResult response = getData(hjyCommunityDtos);
        return response;
    }

    @PostMapping
    public BaseResponse<Integer> add(@RequestBody HjyCommunity community)
    {
        int data = service.AddHjyCommunity(community);
        return BaseResponse.success(data);
    }

    @DeleteMapping( "/{communityIds}")
    public BaseResponse<Integer> delete(@PathVariable List<Long> communityIds){
        int data = service.deleteHjyCommunity(communityIds);
        return BaseResponse.success(data);
    }
    @GetMapping( "/{communityIds}")
    public BaseResponse<HjyCommunity> getCommunityById(@PathVariable Long communityIds){
        HjyCommunity data = service.getCommunityById(communityIds);
        return BaseResponse.success(data);
    }
    @PutMapping
    public BaseResponse<Integer> updateCommunityById(@RequestBody  HjyCommunity community){
        int data = service.updateCommunity(community);
        return BaseResponse.success(data);
    }
}
