package com.tca.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author zhoua
 * @Date 2020/4/12
 * 鉴权配置管理器
 */
public interface AuthorizeConfigurerManager {

    /**
     * 鉴权配置管理
     * @param config
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
