package com.zgy.hjy_community;


import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class HjyCommunityApplicationTests {
    @Autowired
    SysMenuService service;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Test
    void contextLoads() {
        userDetailService.loadUserByUsername("admin");
    }

}
