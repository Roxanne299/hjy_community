package com.zgy.hjy_community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.zgy.hjy_community.**.mapper")
public class HjyCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(HjyCommunityApplication.class, args);
    }

}
