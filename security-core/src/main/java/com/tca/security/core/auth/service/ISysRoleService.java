package com.tca.security.core.auth.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tca.security.core.auth.entity.SysRole;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-16
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询
     * @param page
     * @param sysRole
     * @return
     */
    IPage<SysRole> page(IPage page, SysRole sysRole);

    /**
     * 通过id获取
     * @param id
     * @param queryPermissionFlag
     * @return
     */
    SysRole getById(Long id, boolean queryPermissionFlag);

    /**
     * 添加角色
     * @param sysRole
     */
    void insert(SysRole sysRole);

    /**
     * 更新角色
     * @param role
     */
    void update(SysRole role);

    /**
     * 删除 role
     * @param id
     * @return
     */
    boolean delete(Long id);

}
