package com.tca.security.core.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.mapper.SysPermissionMapper;
import com.tca.security.core.auth.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-16
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getPermissionByUserId(Long userId) {
        return baseMapper.getPermissionByUserId(userId);
    }

}
