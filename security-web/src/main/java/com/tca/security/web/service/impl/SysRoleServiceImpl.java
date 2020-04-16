package com.tca.security.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tca.security.web.entity.SysRole;
import com.tca.security.web.mapper.SysRoleMapper;
import com.tca.security.web.service.ISysRoleService;
import org.springframework.stereotype.Service;

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

}
