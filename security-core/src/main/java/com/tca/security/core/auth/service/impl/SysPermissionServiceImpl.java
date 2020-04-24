package com.tca.security.core.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.mapper.SysPermissionMapper;
import com.tca.security.core.auth.service.ISysPermissionService;
import com.tca.utils.ValidateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public boolean delete(Long id) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<SysPermission> sysPermissionList = baseMapper.selectList(queryWrapper);
        if (ValidateUtils.isNotEmpty(sysPermissionList)) {
            List<Long> idList = sysPermissionList.stream().map(SysPermission::getId).distinct().collect(Collectors.toList());
            idList.forEach(idParam -> delete(idParam));
        }
        baseMapper.deleteById(id);
        return true;
    }

}
