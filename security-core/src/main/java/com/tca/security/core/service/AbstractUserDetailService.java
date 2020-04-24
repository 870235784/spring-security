package com.tca.security.core.service;

import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.service.ISysPermissionService;
import com.tca.utils.ValidateUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author zhoua
 * @Date 2020/4/17
 */
abstract public class AbstractUserDetailService implements UserDetailsService {

    @Autowired
    private ISysPermissionService sysPermissionService;


    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        // 1.获取用户
        SysUser sysUser = getUserByPrincipal(principal);
        // 2.获取对应权限
        setUserPermissions(sysUser);
        // 3.返回
        return sysUser;
    }

    /**
     * 根据principal获取用户
     * @param principal
     * @return
     */
    public abstract SysUser getUserByPrincipal(String principal);

    /**
     * 设置用户权限
     * @param sysUser
     */
    private void setUserPermissions(SysUser sysUser) {
        // 1.校验
        if (ValidateUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 2.根据用户获取权限
        List<SysPermission> sysPermissionList = sysPermissionService.getPermissionByUserId(sysUser.getId());
        if (ValidateUtils.isNotEmpty(sysPermissionList)) {
            sysUser.setPermissions(sysPermissionList);
            List<GrantedAuthority> authorityList = Lists.newArrayList();
            sysPermissionList.forEach(sysPermission -> {
                authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
            });
            sysUser.setAuthorities(authorityList);
        }
    }
}
