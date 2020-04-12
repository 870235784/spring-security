package com.tca.security.core.service;

import com.tca.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("customerUserDetailService")
public class CustomerUserDetailService implements UserDetailsService{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 动态验证用户名
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.验证用户名
        if (!username.equals(securityProperties.getAuthentication().getUsername().trim())) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 2.密码校验
        String encodePassword = passwordEncoder.encode(securityProperties.getAuthentication().getPassword());

        // 3.返回
        return new User(username, encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, sys:role"));
    }

}
