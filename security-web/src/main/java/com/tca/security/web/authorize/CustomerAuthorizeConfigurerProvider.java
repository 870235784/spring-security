package com.tca.security.web.authorize;

import com.tca.security.core.authorize.AuthorizeConfigurerProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/4/12
 */
@Component
public class CustomerAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider{

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
            // hasAuthority: 拥有sys:user权限的用户, 可以访问任意请求方式的/user
            .antMatchers("/user").hasAuthority("sys:user")
            // 拥有sys:role权限的用户, 可以访问GET请求方式的/user
            .antMatchers(HttpMethod.GET, "/role").hasAnyAuthority("sys:role")
            // 表示拥有 sys:permission 权限的用户 或者 拥有 ROLE_ADMIN 角色的用户 可以访问GET请求方式的 /permission (注意:角色会加上前缀 ROLE_，即真实是 ROLE_ADMIN)
            .antMatchers(HttpMethod.GET, "/permission").access("hasAuthority('sys:permission') or hasAnyRole('ADMIN')");
    }
}
