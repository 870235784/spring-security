package com.tca.security.core.authorize;

import com.tca.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoua
 * @Date 2020/4/12
 */
@Component
@Slf4j
public class BasicAuthorizeConfigurerManager implements AuthorizeConfigurerManager {

    @Autowired
    private List<AuthorizeConfigurerProvider> authorizeConfigurerProviderList;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        if (ValidateUtils.isEmpty(authorizeConfigurerProviderList)) {
            log.info("无鉴权配置实现");
            return;
        }
        for(AuthorizeConfigurerProvider authorizeConfigurerProvider : authorizeConfigurerProviderList) {
            authorizeConfigurerProvider.configure(config);
        }
    }
}
