package com.zgy.hjy_community.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.zgy.hjy_community.common.core.BaseException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * @author roxanne_waar
 * @date 2024/1/24 13:48
 * @description excel操作工具类
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 封装导出excel表格方法
     * @param response
     * @param list
     * @param cls
     * @param titleName
     * @param sheetName
     * @throws IOException
     */
    public  static void exportExcel(HttpServletResponse response, List<?> list,Class<?> cls,String titleName,String sheetName) throws Exception {

        ServletOutputStream outputStream = null;
        Workbook sheets = null;

        try {
            ExportParams params = new ExportParams(titleName,sheetName);
            outputStream = response.getOutputStream();
            sheets = ExcelExportUtil.exportExcel(params, cls, list);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode("小区信息列表.xls","UTF-8"));
            sheets.write(outputStream);
        }
        catch (Exception e){
            throw new BaseException(500,"导出excel表格失败");
        }finally {
            outputStream.close();
            sheets.close();
        }
    }
}
