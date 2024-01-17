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
    @Autowired
    HjyCommunityMapper mapper;
    @Override
    public List<HjyCommunityDto> selectHjyCommunityList(HjyCommunity community) {

        return mapper.queryList(community);
    }
}
