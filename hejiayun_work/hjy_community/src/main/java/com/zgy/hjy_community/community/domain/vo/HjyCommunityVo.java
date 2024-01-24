package com.zgy.hjy_community.community.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author roxanne_waar
 * @date 2024/1/24 14:44
 * @description HjyCommunityVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HjyCommunityVo implements Serializable {
    private static final long serialVersionUID = -6587222499565117492L;

    /** 小区id */
     @JsonFormat(shape = JsonFormat.Shape.STRING)
     private Long communityId;
 
     /** 小区名称 */
     private String communityName;
}
