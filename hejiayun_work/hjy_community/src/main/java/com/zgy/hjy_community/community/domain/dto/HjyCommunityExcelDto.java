package com.zgy.hjy_community.community.domain.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author roxanne_waar
 * @date 2024/1/24 11:21
 * @description HjyCommunityExcelDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("和家云社区")
public class HjyCommunityExcelDto implements Serializable {
    @ExcelIgnore
    private static final long serialVersionUID = -3804646124636470671L;
    /**
     * 小区id
     */
    @Excel(name = "序号")
    private Long communityId;
    /**
     * 小区名称
     */
    @Excel(name = "小区名称")
    private String communityName;
    /**
     * 小区编码
     */
    @Excel(name = "小区编码")
    private String communityCode;
    /**
     * 省区名称
     */
    @Excel(name = "省")
    private String communityProvinceName;
    /**
     * 市区名称
     */
    @Excel(name = "市")
    private String communityCityName;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间",format = "yyyy年MM月dd日")
    private Date createTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
