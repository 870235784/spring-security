package com.tca.security.core.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/24
 * 读取 application.yml 文件, 将tca.security.authentication的属性注入到bean
 */
@Component
@ConfigurationProperties(prefix = "tca.security")
@Data
@ToString
public class SecurityProperties {

    /**
     * 将application.yml 中的 tca.security.authentication 下面的值绑定到此对象中
     * 属性名必须与application.yml 配置文件定义的相同
     */
    @Autowired
    private AuthenticationProperties authentication;

}
