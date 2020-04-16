package com.tca.security.core.service;

import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("customerUserDetailService")
public class CustomerUserDetailService extends AbstractUserDetailService {

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public SysUser getUserByPrincipal(String principal) {
        return sysUserService.getUserByUsername(principal);
    }
}
