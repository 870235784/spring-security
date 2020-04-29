package com.tca.security.core.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.entity.SysRole;
import com.tca.security.core.auth.mapper.SysPermissionMapper;
import com.tca.security.core.auth.mapper.SysRoleMapper;
import com.tca.security.core.auth.service.ISysRoleService;
import com.tca.utils.ValidateUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public IPage<SysRole> page(IPage page, SysRole sysRole) {
        QueryWrapper<SysRole> wrapperQuery = new QueryWrapper<>();
        if (ValidateUtils.isNotEmpty(sysRole.getName())) {
            wrapperQuery.like("name", sysRole.getName());
        }
        return sysRoleMapper.selectPage(page, wrapperQuery);
    }

    @Override
    public SysRole getById(Long id, boolean queryPermissionFlag) {
        SysRole sysRole = getById(id);
        if (queryPermissionFlag) {
            List<SysPermission> sysPermissionList = sysPermissionMapper.getPermissionByRoleId(id);
            sysRole.setPerList(ValidateUtils.isEmpty(sysPermissionList)? Lists.newArrayList(): sysPermissionList);
            sysRole.setPerIds(ValidateUtils.isEmpty(sysPermissionList)? Lists.newArrayList():
                sysPermissionList.stream().map(SysPermission::getId).distinct().collect(Collectors.toList()));
        }
        return sysRole;
    }

    @Override
    @Transactional
    public void insert(SysRole sysRole) {
        // 1.sysRole入库
        baseMapper.insert(sysRole);
        // 2.role-permission入库
        if (ValidateUtils.isEmpty(sysRole.getPerIds())) {
            return;
        }
        sysRoleMapper.insertBatchRolePermissionRel(sysRole.getId(), sysRole.getPerIds());
    }

    @Override
    @Transactional
    public void update(SysRole sysRole) {
        // 1.sysRole入库
        baseMapper.updateById(sysRole);
        // 2.删除原有的role-permission
        sysRoleMapper.deleteRolePermissionRelByRoleId(sysRole.getId());
        // 2.role-permission入库
        if (ValidateUtils.isEmpty(sysRole.getPerIds())) {
            return;
        }
        sysRoleMapper.insertBatchRolePermissionRel(sysRole.getId(), sysRole.getPerIds());
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1.删除role-permission
        sysRoleMapper.deleteRolePermissionRelByRoleId(id);
        // 2.删除role
        baseMapper.deleteById(id);
        return true;
    }
}
