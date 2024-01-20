package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.domain.dto.SysAreaDto;
import com.zgy.hjy_community.system.domain.entity.HjySystemArea;
import com.zgy.hjy_community.system.mapper.HjySystemAreaMapper;
import com.zgy.hjy_community.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/1/18 15:06
 * @description SysAreaServiceImpl
 */
@Service
public class SysAreaServiceImpl implements SysAreaService {

    @Autowired
    HjySystemAreaMapper mapper;


    @Override
    public List<SysAreaDto> findAreaAsTree() {

        List<HjySystemArea> all = mapper.findAll();
        List<SysAreaDto> tree = all.stream().filter(area -> area.getParentid().equals(0))
                .map(area -> {
                    SysAreaDto sysAreaDto = new SysAreaDto();
                    sysAreaDto.setName(area.getName());
                    sysAreaDto.setCode(area.getCode());
                    List<SysAreaDto> childs = getChildrens(all, area);
                    if(childs.isEmpty()) childs = null;
                    sysAreaDto.setChildren(childs);
                    return sysAreaDto;
                })
                .collect(Collectors.toList());
        return tree;
    }

    /**
     * 递归根据父节点找到所有的子节点
     * @param allArea
     * @param area
     * @return
     */
    public List<SysAreaDto> getChildrens(List<HjySystemArea> allArea, HjySystemArea area){
        List<SysAreaDto> childrens = allArea.stream()
                .filter(hjySystemArea -> hjySystemArea.getParentid().equals(area.getCode()))
                .map(hjySystemArea -> {
                SysAreaDto sysAreaDto = new SysAreaDto();
                sysAreaDto.setCode(hjySystemArea.getCode());
                sysAreaDto.setName(hjySystemArea.getName());
                List<SysAreaDto> childs = getChildrens(allArea, hjySystemArea);
                if(childs.isEmpty()) childs = null;
                sysAreaDto.setChildren(childs);
                sysAreaDto.setChildren(childs);
                return sysAreaDto;
            })
                .collect(Collectors.toList());
        return childrens;
    }
}
