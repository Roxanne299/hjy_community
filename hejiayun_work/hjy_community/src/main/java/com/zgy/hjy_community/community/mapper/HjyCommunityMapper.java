package com.zgy.hjy_community.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/17 15:04
 * @description CommunityMapper
 */

public interface HjyCommunityMapper extends BaseMapper<HjyCommunity> {
    @Select("<script>SELECT \n" +
            "    *,\n" +
            "    s1.`name` AS communityProvinceName,\n" +
            "    s2.`name` AS communityCityName,\n" +
            "    s3.`name` AS communityTownName\n" +
            "FROM hjy_community hc \n" +
            "LEFT JOIN sys_area s1 ON hc.`community_province_code` = s1.`code`\n" +
            "LEFT JOIN sys_area s2 ON hc.`community_city_code` = s2.`code`\n" +
            "LEFT JOIN sys_area s3 ON hc.`community_town_code` = s3.`code`" +
            "<where>" +
            "<if test=\"communityName !=null and communityName != ''\">" +
            "hc.community_name like concat('%',#{communityName},'%')" +
            "</if> " +

            "<if test=\"communityCode !=null and communityCode != ''\">" +
            "and hc.community_code = #{communityCode}" +
            "</if> " +
            "</where>"+
            "</script>")
    List<HjyCommunityDto> queryList(HjyCommunity community);
}
