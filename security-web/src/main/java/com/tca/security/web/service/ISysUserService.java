package com.tca.security.web.service;

import com.tca.security.web.entity.SysUser;

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
}
