package com.zgy.easypoi_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zgy.easypoi_web.mapper")
public class EasypoiWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasypoiWebApplication.class, args);
    }

}
