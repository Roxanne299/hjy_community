package com.zgy.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/22 10:52
 * @description User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("user")
public class User implements Serializable {
    @ExcelIgnore
    private static final long serialVersionUID = 8396566671249699691L;
    @Excel(name = "学生编号")
    Integer id;
    @Excel(name = "姓名")
    String name;

    @Excel(name = "学生头像",width = 20,height = 20,type = 2,imageType = 1)
    String photo;
    

}
