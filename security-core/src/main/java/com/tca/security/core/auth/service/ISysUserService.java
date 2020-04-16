package com.tca.security.core.auth.service;

import com.tca.security.core.auth.entity.SysUser;

/**
 * @author zhoua
 * @Date 2020/4/16
 */
public interface ISysUserService {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     */
    SysUser getUserByMobile(String mobile);
}
