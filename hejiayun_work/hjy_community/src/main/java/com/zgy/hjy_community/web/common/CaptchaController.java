package com.zgy.hjy_community.web.common;

import com.wf.captcha.SpecCaptcha;
import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.core.BaseResponse;
import com.zgy.hjy_community.common.utils.ChainedMap;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author roxanne_waar
 * @date 2024/2/5 15:41
 * @description 验证码控制器
 */
@RestController
public class CaptchaController {

    @Autowired
    RedisCache redisCache;

    /**
     * 生成验证码
     * @return
     */
    @GetMapping("/captchaImage")
    public Map getCaptcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,4);

        String simpleUUID = UUIDUtils.getSimpleUUID();
        String code = specCaptcha.text().toLowerCase();
        String key = Constants.CAPTCHA_CODE_KEY + simpleUUID;

        redisCache.setCacheObject(key,code,5, TimeUnit.MINUTES);

        return ChainedMap.create().set("uuid",simpleUUID).set("img",specCaptcha.toBase64());
    }
}
