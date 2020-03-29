package com.tca.security.core.mobile;

import com.tca.utils.ValidateUtils;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/29
 * 适配器模式
 */
@Component("mobileAuthenticationProvider")
@Data
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1.转换类型
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        // 2.获取用户手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        // 3.查询用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        // 用户不存在
        if (ValidateUtils.isEmpty(userDetails)) {
            throw new AuthenticationServiceException("当前手机号尚未注册");
        }
        // 4.重新构建用户信息
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(mobile, userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
