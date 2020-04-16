package com.tca.security.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.web.entity.SysUser;
import com.tca.security.web.mapper.SysUserMapper;
import com.tca.security.web.service.ISysUserService;
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

}
