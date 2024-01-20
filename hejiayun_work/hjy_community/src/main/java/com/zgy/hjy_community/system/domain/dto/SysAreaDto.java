package com.zgy.hjy_community.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/18 14:54
 * @description 区域表(SysArea)Dto类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAreaDto implements Serializable {
    private static final long serialVersionUID = 5288658714897229358L;

    /**
     * 城市编码
     */
    private Integer code;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 子节点集合
     */
    private List<SysAreaDto> children;
}
