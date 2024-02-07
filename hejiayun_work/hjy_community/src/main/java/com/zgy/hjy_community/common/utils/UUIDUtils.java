package com.zgy.hjy_community.common.utils;

import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.UUID;

/**
 * @author roxanne_waar
 * @date 2024/2/5 11:08
 * @description UUID工具类
 */
public class UUIDUtils {

    /**
     * 随机获取UUID
     * @return
     */
    public static String getRandomUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 随机获取简化UUID替换横线
     * @return
     */
    public static  String getSimpleUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


}
