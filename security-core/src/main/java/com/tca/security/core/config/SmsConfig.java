package com.tca.security.core.config;

import com.tca.security.core.mobile.IsmsSender;
import com.tca.security.core.mobile.DefaultSmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoua
 * @Date 2020/3/27
 */
@Configuration
public class SmsConfig {

    /**
     * @ConditionalOnMissingBean(IsmsSender.class)
     * 默认采用DefaultSmsSender实例, 但如果容器中有其他 IsmsSender 类型的实例, 则当前实例失效
     */
    @Bean
    @ConditionalOnMissingBean(IsmsSender.class)
    public IsmsSender smsSend() {
        return new DefaultSmsSender();
    }
}
