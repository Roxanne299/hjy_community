package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.system.domain.dto.SysAreaDto;
import com.zgy.hjy_community.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/18 14:47
 * @description SystemAreaController
 */
@RestController
@RequestMapping("/system/area")
public class SystemAreaController {

    @Autowired
    SysAreaService service;

    @GetMapping("/tree")
    public BaseResponse<List<SysAreaDto>> getAreaAsTree(){
        List<SysAreaDto> areaAsTree = service.findAreaAsTree();
        return BaseResponse.success(areaAsTree);
    }
}
