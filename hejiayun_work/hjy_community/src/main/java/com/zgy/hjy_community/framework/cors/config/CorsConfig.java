package com.zgy.hjy_community.framework.cors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author roxanne_waar
 * @date 2024/2/6 15:09
 * @description 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许CORS（跨域资源共享）协议
        configuration.setAllowCredentials(true);
        // 受任意域名的请求
        configuration.addAllowedOrigin("*");
        // 表示允许的请求方法
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(source);
    }
}
