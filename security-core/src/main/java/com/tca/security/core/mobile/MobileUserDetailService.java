package com.tca.security.core.mobile;

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


    /**
     * 动态验证用户名
     * @param mobile
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 验证手机号是否存在
        if (mobileExist(mobile)) {
            return new User(mobile, "", true, true, true, true,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
        }

        return null;
    }

    /**
     * 校验手机号是否存在
     * @param mobile
     * @return
     */
    private boolean mobileExist(String mobile) {
        return true;
    }


}
