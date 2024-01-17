package com.zgy.hjy_community.community.service;

import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/17 15:19
 * @description HjyCommunityService
 */
public interface HjyCommunityService {
    /**
     * 根据条件查询和家云小区列表
     * @param community
     * @return
     */
    List<HjyCommunityDto> selectHjyCommunityList(HjyCommunity community);
}
