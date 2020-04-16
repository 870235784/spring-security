package com.tca.security.core.mobile;

import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.service.ISysUserService;
import com.tca.security.core.service.AbstractUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("mobileUserDetailService")
public class MobileUserDetailService extends AbstractUserDetailService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public SysUser getUserByPrincipal(String principal) {
        return sysUserService.getUserByMobile(principal);
    }
}
