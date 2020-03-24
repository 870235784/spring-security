package com.tca.security.core.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/24
 */
@Data
@ToString
@Component
public class AuthenticationProperties {

    /**
     * 默认登录请求页面
     */
    private String loginPage = "/login/page";

    /**
     * 默认登陆请求
     */
    private String loginProcessingUrl = "/login/form";

    /**
     * 默认登录请求用户名参数
     */
    private String usernameParameter = "username";

    /**
     * 默认登录请求用户密码参数
     */
    private String passwordParameter = "password";

    /**
     * 默认静态资源请求路径
     */
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

    /**
     * 默认用户名
     */
    private String username = "admin";

    /**
     * 默认密码
     */
    private String password = "123456";

}
