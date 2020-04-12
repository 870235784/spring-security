package com.tca.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author zhoua
 * @Date 2020/4/12
 * 鉴权配置
 */
public interface AuthorizeConfigurerProvider {

    /**
     * 鉴权配置
     * @param config
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
