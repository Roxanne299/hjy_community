package com.zgy.hjy_community.community.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zgy.hjy_community.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 小区 (HjyCommunity)实体类
 *
 * @author roaxnne_waar
 * @since 2024-01-16 13:56:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HjyCommunity extends BaseEntity {
    /**
     * 小区id
     */
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long communityId;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 小区编码
     */
    private String communityCode;
    /**
     * 省区划码
     */
    private String communityProvinceCode;
    /**
     * 市区划码
     */
    private String communityCityCode;
    /**
     * 区县区划码
     */
    private String communityTownCode;
    /**
     * 详细地址
     */
    private String communityDetailedAddress;
    /**
     * 经度
     */
    private String communityLongitude;
    /**
     * 纬度
     */
    private String communityLatitude;
    /**
     * 物业id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
    /**
     * 排序
     */
    private Integer communitySort;

}

