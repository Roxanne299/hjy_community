package com.zgy.hjy_community.community.service;

import com.zgy.hjy_community.HjyCommunityApplication;
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
     * @return 社区列表
     */
    List<HjyCommunityDto> selectHjyCommunityList(HjyCommunity community);

    /**
     * 添加社区
     * @param community
     * @return 成功条数
     */
    int AddHjyCommunity(HjyCommunity community);

    /**
     * 批量删除社区
     * @param communityIds
     * @return 成功条数
     */
    int deleteHjyCommunity(List<Long> communityIds);

    /**
     * 根据id查询小区
     * @param communityId
     * @return 小区信息
     */
    HjyCommunity getCommunityById(Long communityId);
    /**
     * 根据id修改小区
     * @param community
     * @return 成功条数
     */
    int updateCommunity(HjyCommunity community);
}
