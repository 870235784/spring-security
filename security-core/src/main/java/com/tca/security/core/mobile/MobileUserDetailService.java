package com.tca.security.core.mobile;

import com.tca.security.core.properties.SecurityProperties;
import com.tca.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("mobileUserDetailService")
public class MobileUserDetailService implements UserDetailsService{

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 动态验证用户名
     * @param mobile
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 验证手机号是否存在
        String username = getUserByMobile(mobile);
        if (ValidateUtils.isNotEmpty(username)) {
            return new User(username, "", true, true, true, true,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, sys:role"));
        }

        return null;
    }

   /* *//**
     * 校验手机号是否存在
     * @param mobile
     * @return
     *//*
    private boolean mobileExist(String mobile) {
        return true;
    }*/

    /**
     * 根据手机号获取用户名
     * @param mobile
     * @return
     */
    private String getUserByMobile(String mobile) {
        return securityProperties.getAuthentication().getUsername();
    }


}
