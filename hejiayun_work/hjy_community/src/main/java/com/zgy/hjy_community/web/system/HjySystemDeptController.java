package com.zgy.hjy_community.web.system;

import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.system.service.HjySysDeptService;
import com.zgy.hjy_community.system.domain.entity.HjySystemDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/20 12:47
 * @description HjySystemDeptController
 */
@RestController
@RequestMapping("/system/dept")
public class HjySystemDeptController {
    @Autowired
    HjySysDeptService service;

    @GetMapping("/list")
    public BaseResponse<List<HjySystemDept>> getSystemDeptList(){
        return BaseResponse.success(service.getSysDeptList());
    }
}
