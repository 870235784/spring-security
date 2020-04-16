package com.tca.security.core.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tca.security.core.auth.entity.SysPermission;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-16
 */
public interface ISysPermissionService extends IService<SysPermission> {


    /**
     * 根据用户id获取对应权限
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(Long userId);
}
