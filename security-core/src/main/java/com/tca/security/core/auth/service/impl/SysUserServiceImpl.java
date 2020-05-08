package com.tca.security.core.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.core.auth.entity.SysRole;
import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.mapper.SysRoleMapper;
import com.tca.security.core.auth.mapper.SysUserMapper;
import com.tca.security.core.auth.service.ISysUserService;
import com.tca.utils.ValidateUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByUsername(String username) {
        if (ValidateUtils.isEmpty(username)) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        if (ValidateUtils.isEmpty(mobile)) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> page(IPage page, SysUser sysUser) {
        QueryWrapper<SysUser> wrapperQuery = new QueryWrapper<>();
        wrapperQuery.eq("is_enabled", 1);
        if (ValidateUtils.isNotEmpty(sysUser.getUsername())) {
            wrapperQuery.like("username", sysUser.getUsername());
        }
        if (ValidateUtils.isNotEmpty(sysUser.getMobile())) {
            wrapperQuery.eq("mobile", sysUser.getMobile());
        }
        return sysUserMapper.selectPage(page, wrapperQuery);
    }

    @Override
    public SysUser getById(Long id, boolean queryRoleFlag) {
        SysUser sysUser = getById(id);
        if (queryRoleFlag) {
            List<SysRole> sysRoleList = sysRoleMapper.getRoleByUserId(id);
            sysUser.setRoleList(ValidateUtils.isEmpty(sysRoleList)? Lists.newArrayList(): sysRoleList);
            sysUser.setRoleIds(ValidateUtils.isEmpty(sysRoleList)? Lists.newArrayList():
                    sysRoleList.stream().map(SysRole::getId).distinct().collect(Collectors.toList()));
        }
        return sysUser;
    }


    @Override
    @Transactional
    public void insert(SysUser user) {
        // 1.sysRole入库
        baseMapper.insert(user);
        // 2.user-role入库
        if (ValidateUtils.isEmpty(user.getRoleIds())) {
            return;
        }
        sysRoleMapper.insertBatchUserRoleRel(user.getId(), user.getRoleIds());
    }

    @Override
    @Transactional
    public void update(SysUser user) {
        // 1.sysUser入库
        baseMapper.updateById(user);
        // 2.删除原有的user-role
        sysRoleMapper.deleteUserRoleRelByUserId(user.getId());
        // 2.role-permission入库
        if (ValidateUtils.isEmpty(user.getRoleIds())) {
            return;
        }
        sysRoleMapper.insertBatchUserRoleRel(user.getId(), user.getRoleIds());
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        SysUser sysUser = getById(id);
        sysUser.setEnabled(false);
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return true;
    }

}
