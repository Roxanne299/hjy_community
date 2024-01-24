package com.zgy.hjy_community.web.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.zgy.hjy_community.common.controller.BaseController;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ExcelUtils;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityDto;
import com.zgy.hjy_community.community.domain.dto.HjyCommunityExcelDto;
import com.zgy.hjy_community.community.domain.entity.HjyCommunity;
import com.zgy.hjy_community.community.service.HjyCommunityService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author roxanne_waar
 * @date 2024/1/24 11:29
 * @description 导出excel公共类
 */
@RestController
@RequestMapping("/exportExcel")
public class ExportExcelController extends BaseController {
    @Autowired
    HjyCommunityService service;

    @RequestMapping("/exportCommunityExcel")
    public BaseResponse exportCommunityExcel(HjyCommunity community, HttpServletResponse response) throws Exception {
        startPage();
        List<HjyCommunityDto> hjyCommunityDtos = service.selectHjyCommunityList(community);
        List<HjyCommunityExcelDto> excelDtos = hjyCommunityDtos.stream().map(hjyCommunityDto -> {
            HjyCommunityExcelDto excelDto = new HjyCommunityExcelDto();
            excelDto.setCommunityId(hjyCommunityDto.getCommunityId());
            excelDto.setCommunityCode(hjyCommunityDto.getCommunityCode());
            excelDto.setCommunityName(hjyCommunityDto.getCommunityName());
            excelDto.setRemark(hjyCommunityDto.getRemark());
            excelDto.setCommunityProvinceName(hjyCommunityDto.getCommunityProvinceName());
            excelDto.setCommunityCityName(hjyCommunityDto.getCommunityCityName());
            excelDto.setCreateTime(hjyCommunityDto.getCreateTime());
            return excelDto;
        }).collect(Collectors.toList());

        ExcelUtils.exportExcel(response,excelDtos, HjyCommunityExcelDto.class,"和家云社区","sheet1");
        return BaseResponse.success("导出成功");
    }
}
