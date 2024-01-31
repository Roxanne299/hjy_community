package com.zgy.springsecurity_demo.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.zgy.springsecurity_demo.common.BaseResponse;
import com.zgy.springsecurity_demo.pojo.VrCode;
import com.zgy.springsecurity_demo.service.LoginService;
import com.zgy.springsecurity_demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author roxanne_waar
 * @date 2024/1/29 9:39
 * @description MyController
 */
@RestController
public class MyController {

    @Autowired
    Producer producer;
    @Autowired
    LoginService service;
    @Autowired
    RedisCache redisCache;

    @PostMapping("/login")
    public BaseResponse login(String username, String password) {
        String token = service.login(username, password);
        return BaseResponse.success(token);
    }

    @RequestMapping("/hello")
    public String getIndex() {
        return "hello!";
    }

    @GetMapping("/vrcode/img")
    public BaseResponse getVrImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.创建验证码文本
        String capText = producer.createText();

        //2.创建验证码图片
        BufferedImage bufferedImage = producer.createImage(capText);

        //3.将验证码文本放进 redis 中
        VrCode code = new VrCode(capText);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String key = "CAPTCHA_CODE_KEY:" + uuid;
        redisCache.setCacheObject(key,code,60,TimeUnit.SECONDS);


        String png = encodeBufferedImageToBase64(bufferedImage, "png");
        Map<String, Object> map = new HashMap<>();
        map.put("img", png);
        map.put("uuid", uuid);
        return BaseResponse.success(map);
    }

    public String encodeBufferedImageToBase64(BufferedImage image, String type) {
        // 创建一个用于输出的 ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 将 BufferedImage 写入 ByteArrayOutputStream
            ImageIO.write(image, type, baos);
            // 刷新流
            baos.flush();
            // 将字节数据转换为字节数组
            byte[] imageBytes = baos.toByteArray();
            // 使用 Base64 编码字节数组
            String base64String = Base64.getEncoder().encodeToString(imageBytes);
            // 关闭 ByteArrayOutputStream
            baos.close();
            // 返回 Base64 编码的字符串
            return base64String;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
