package com.zgy.easypoi_web.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author roxanne_waar
 * @date 2024/1/23 19:14
 * @description Cources
 */
@ExcelTarget("cources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cources implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = -1858849753278642756L;
    @Excel(name = "编号")
    public String cid;

    @Excel(name = "订单编号")
    public String orderno;

    @Excel(name = "课程名称")
    public String cname;

    @Excel(name = "介绍")
    public String breif;

    @Excel(name = "价格")
    public Double price;

}
