package com.tca.security.core.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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


    /**
     * 分页查询
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUser> page(IPage page, SysUser sysUser);

    /**
     * 根据id获取用户
     * @param id
     * @param queryRoleFlag
     * @return
     */
    SysUser getById(Long id, boolean queryRoleFlag);

    /**
     * 新增用户
     * @param user
     */
    void insert(SysUser user);

    /**
     * 更新用户
     * @param user
     */
    void update(SysUser user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean delete(Long id);
}
