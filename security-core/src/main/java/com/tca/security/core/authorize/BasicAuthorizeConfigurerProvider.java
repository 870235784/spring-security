package com.tca.security.core.authorize;

import com.tca.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 基本鉴权
 * @author zhoua
 * @Date 2020/4/12
 */
@Component
@Order
public class BasicAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // permitAll: 满足当前url条件放行
        config.antMatchers(securityProperties.getAuthentication().getIgnoreUrls()).permitAll();
        // 所有通过http访问的请求都需要认证
        config.anyRequest().authenticated();
    }
}
