package com.zgy.springsecurity_demo.config;

import com.zgy.springsecurity_demo.filter.MyCorsFilter;
import com.zgy.springsecurity_demo.filter.VrcodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * @author roxanne_waar
 * @date 2024/1/29 10:21
 * @description SecurityConfig
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    Filter securityFilter;

    @Autowired
    MyCorsFilter myCorsFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    VrcodeFilter vrcodeFilter;
    @Bean
    public PasswordEncoder getPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 新建一个AuthenticationManager对象
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用crsf
        http.csrf().disable()
//                //不会创建会话，每个请求都将被视为独立的请求。
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                 .and()
                // 配置请求 放行login 其余请求都需要授权
                .authorizeRequests().antMatchers("/login","/vrcode/img").permitAll()
                .anyRequest().authenticated();
        //指定过滤器的顺序
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        // 验证用户名之前验证我们的验证码
        http.addFilterBefore(vrcodeFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(myCorsFilter,VrcodeFilter.class);
        //springsecurity允许跨域请求
        http.cors();
        // 设置统一异常接口
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
    }

}
