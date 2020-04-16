package com.tca.security.web.test;

import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.service.ISysPermissionService;
import com.tca.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhoua
 * @Date 2020/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysPermissionTest {

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Test
    public void testGet() {
        SysPermission sysPermission = sysPermissionService.getById(11);
        log.info("sysPermission = {}", sysPermission);
    }

    @Test
    public void testGetByUserId() {
        List<SysPermission> permissionList = sysPermissionService.getPermissionByUserId(9L);
        log.info("permissionList = {}", permissionList);
        log.info("permissionList is empty ?  {}", ValidateUtils.isEmpty(permissionList));
    }
}
