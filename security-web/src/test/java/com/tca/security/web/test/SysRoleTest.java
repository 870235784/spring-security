package com.tca.security.web.test;

import com.tca.security.web.entity.SysRole;
import com.tca.security.web.service.ISysRoleService;
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
public class SysRoleTest {

    @Autowired
    private ISysRoleService sysRoleService;

    @Test
    public void testSysRole() {
        SysRole sysRole = sysRoleService.getById(9);
        log.info("sysRole = {}", sysRole);
    }
}
