package com.tca.security.core.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.mapper.SysUserMapper;
import com.tca.security.core.auth.service.ISysUserService;
import com.tca.utils.ValidateUtils;
import org.springframework.stereotype.Service;

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

}
