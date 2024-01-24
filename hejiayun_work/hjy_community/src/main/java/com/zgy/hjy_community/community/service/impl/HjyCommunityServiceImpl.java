package com.zgy.hjy_community.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;
import com.zgy.hjy_community.community.domain.vo.HjyCommunityVo;
import com.zgy.hjy_community.community.mapper.HjyCommunityMapper;
import com.zgy.hjy_community.community.service.HjyCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<HjyCommunityVo> getCommunityList(HjyCommunity community) {
        List<HjyCommunityDto> communityDtos = selectHjyCommunityList(community);
        List<HjyCommunityVo> vos = communityDtos.stream().map(dto -> {
            HjyCommunityVo vo = new HjyCommunityVo();
            vo.setCommunityId(dto.getCommunityId());
            vo.setCommunityName(dto.getCommunityName());
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }


}
