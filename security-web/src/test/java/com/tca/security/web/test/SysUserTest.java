package com.tca.security.web.test;

import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhoua
 * @Date 2020/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysUserTest {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public void testGetUserByUsername() {
        SysUser sysUser = sysUserService.getUserByUsername("admin");
        log.info("sysUser = {}", sysUser);
    }
}
