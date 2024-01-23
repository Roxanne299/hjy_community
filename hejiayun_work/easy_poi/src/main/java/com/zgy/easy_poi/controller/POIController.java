package com.zgy.easy_poi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.zgy.easy_poi.pojo.Course;
import com.zgy.easy_poi.pojo.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/1/22 10:51
 * @description POIController
 */
@RestController
@RequestMapping("/easypoi")
public class POIController {

//    @RequestMapping("/export")
//    public int importExcel(HttpServletRequest request) throws IOException {
//        ExportParams params = new ExportParams("学生表","sheet1");
//        User user = new User(1,"zgy","D:\\desktop\\Snipaste_2024-01-22_14-13-04.png", Arrays.asList(new Course(1,"课程1"),new Course(2,"课程12")));
//        List<User> users = new ArrayList<>();
//        users.add(user);
//
//        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, users);
//        FileOutputStream outputStream = new FileOutputStream("D:\\desktop\\users.xls");
//        sheets.write(outputStream);
//        outputStream.close();
//        sheets.close();
//
//        return 1;
//    }
//    @RequestMapping("/export")
//    public int exportExcel(HttpServletRequest request) throws Exception {
//        ExportParams params = new ExportParams("学生表","sheet1");
//        User user = new User(1,"zgy",readUrlAsBytes("https://cdn.acwing.com/media/user/profile/photo/80738_lg_e91297d1fd.jpg"), Arrays.asList(new Course(1,"课程1"),new Course(2,"课程12")));
//        List<User> users = new ArrayList<>();
//        users.add(user);
//
//        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, users);
//        FileOutputStream outputStream = new FileOutputStream("D:\\desktop\\users.xls");
//        sheets.write(outputStream);
//        outputStream.close();
//        sheets.close();
//
//        return 1;
//    }
    @RequestMapping("/import")
    public int importExcel(HttpServletRequest request) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        importParams.setNeedSave(true);
        importParams.setSaveUrl("D:\\desktop\\");
        FileInputStream inputStream = new FileInputStream("D:\\desktop\\users.xls");
        List<Object> objects = ExcelImportUtil.importExcel(new File("D:\\desktop\\users.xls"), User.class, importParams);
        objects.stream().forEach(System.out::println);


        return 1;
    }
        @RequestMapping("/export")
    public int exportExcel(HttpServletRequest request) throws IOException {
        ExportParams params = new ExportParams("学生表","sheet1");
        User user = new User(1,"zgy","D:\\desktop\\Snipaste_2024-01-22_14-13-04.png");
        List<User> users = new ArrayList<>();
        users.add(user);

        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, users);
        FileOutputStream outputStream = new FileOutputStream(request.getServletContext().getRealPath("/csv")+"users.xls");
        sheets.write(outputStream);
        outputStream.close();
        sheets.close();

        return 1;
    }


    public static byte[] readUrlAsBytes(String urls) throws Exception {
        URL url = new URL(urls);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        URLConnection urlConnection = url.openConnection();
        urlConnection.setReadTimeout(10*1000);
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = inputStream.read(bytes)) != -1){
            byteArrayOutputStream.write(bytes,0,len);
        }
        byte[] result = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        inputStream.close();
        return result;


    }

}
