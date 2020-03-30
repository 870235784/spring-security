package com.tca.security.core.mobile;

import com.tca.security.core.service.CustomerAuthenticationFailureHandler;
import com.tca.security.core.service.CustomerAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/29
 */
@Component("mobileAuthenticationConfig")
public class MobileAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomerAuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Autowired
    private CustomerAuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Autowired
    private MobileUserDetailService mobileUserDetailService;

    // 不能采用这种方式, 否则会报错
    /*@Autowired
    private MobileAuthenticationFilter mobileAuthenticationFilter;*/

    @Autowired
    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        // 1.接收 AuthenticationManager 认证管理器, RememberMeServices
        mobileAuthenticationFilter.setAuthenticationManager(
                http.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setRememberMeServices(
                http.getSharedObject(RememberMeServices.class));
        // 2.采用哪个成功、失败处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(
                customerAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(
                customerAuthenticationFailureHandler);
        // 3.为 Provider 指定明确 的mobileUserDetailsService 来查询用户信息
        mobileAuthenticationProvider.setUserDetailsService(mobileUserDetailService);
        // 4.将 provider 绑定到 HttpSecurity 上面，并且将 手机认证加到 用户名密码认证之后
        http.authenticationProvider(mobileAuthenticationProvider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
