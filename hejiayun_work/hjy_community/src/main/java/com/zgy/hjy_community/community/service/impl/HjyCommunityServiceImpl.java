package com.zgy.hjy_community.community.service.impl;

import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;
import com.zgy.hjy_community.community.mapper.HjyCommunityMapper;
import com.zgy.hjy_community.community.service.HjyCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/17 15:20
 * @description HjyCommunityServiceImpl
 */
@Service
public class HjyCommunityServiceImpl implements HjyCommunityService {
    protected final static String COMMUNITY_PREFIX = "COMMUNITY_";

    @Autowired
    HjyCommunityMapper mapper;
    @Override
    public List<HjyCommunityDto> selectHjyCommunityList(HjyCommunity community) {

        return mapper.queryList(community);
    }

    @Override
    public int AddHjyCommunity(HjyCommunity community) {
        community.setCommunityCode(COMMUNITY_PREFIX + System.currentTimeMillis());
        return mapper.insert(community);
    }

    @Override
    public int deleteHjyCommunity(List<Long> communityIds) {
        return mapper.deleteBatchIds(communityIds);
    }

    @Override
    public HjyCommunity getCommunityById(Long communityId) {
        return mapper.selectById(communityId);
    }

    @Override
    public int updateCommunity(HjyCommunity community) {
        return mapper.updateById(community);
    }


}
