package com.zgy.hjy_community.framework.security.config;

import com.zgy.hjy_community.common.exception.AuthenticationExceptionImpl;
import com.zgy.hjy_community.framework.cors.config.CorsConfig;
import com.zgy.hjy_community.framework.security.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * @author roxanne_waar
 * @date 2024/2/5 16:05
 * @description SecurityConfig
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    AuthenticationExceptionImpl authenticationException;

    @Autowired
    CorsFilter corsFilter;

    // 新建密码编码器
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // 新建一个AuthenticationManager对象注入框架
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭cors 因为不使用session
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;

        // 设置不通过认证可以访问的路径
        http.authorizeRequests().antMatchers("/captchaImage","/login").permitAll()
                .anyRequest().authenticated();

        //设置过滤器顺序
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter,SecurityFilter.class);
        //确保在用户注销登录时，响应头中包含必要的跨域资源共享（CORS）字段
        http.addFilterBefore(corsFilter, LogoutFilter.class);

        //设置异常处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationException);



    }

}
