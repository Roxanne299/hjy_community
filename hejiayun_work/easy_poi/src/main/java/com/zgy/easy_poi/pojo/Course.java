package com.zgy.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author roxanne_waar
 * @date 2024/1/22 10:59
 * @description Course
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Excel(name = "课程编号")
    Integer courseId;

    @Excel(name = "课程名")
    String courseName;
}
