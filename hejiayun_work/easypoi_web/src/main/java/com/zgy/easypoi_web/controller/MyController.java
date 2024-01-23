package com.zgy.easypoi_web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.zgy.easypoi_web.pojo.Cources;
import com.zgy.easypoi_web.service.MyService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/23 19:22
 * @description MyController
 */
@Controller
public class MyController {

    @Autowired
    MyService service;

    @RequestMapping("/")
    public String getAllList(HttpServletRequest request){
        List<Cources> courses = service.selectCourceList();
        request.setAttribute("courses",courses);
        return "index";
    }

    @RequestMapping("/course/exportExcel")
    public String export(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=cources.xls");
        List<Cources> cources = service.selectCourceList();
        ExportParams params = new ExportParams("课程表","sheet1");
        Workbook sheets = ExcelExportUtil.exportExcel(params, Cources.class, cources);
        ServletOutputStream outputStream = response.getOutputStream();
        sheets.write(outputStream);
        outputStream.close();
        sheets.close();
        return "index";
    }
    @RequestMapping("/course/importExcel")
    public String importExcel(MultipartFile excelFile) throws Exception {
        InputStream inputStream = excelFile.getInputStream();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Cources> cources = ExcelImportUtil.importExcel(inputStream, Cources.class, params);
        service.AddCourcesList(cources);
        return "index";
    }



}
