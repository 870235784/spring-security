package com.tca.security.core.config;

import com.tca.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zhoua
 * @Date 2020/3/22
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService customerUserDetailService;

    @Autowired
    private AuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *  认证管理器 ：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 用户名密码内存存储方式
        /*auth.inMemoryAuthentication()
                .withUser(securityProperties.getAuthentication().getUsername())
                .password(passwordEncoder().encode(securityProperties.getAuthentication().getPassword()))
                .authorities("ADMIN");*/
        auth.userDetailsService(customerUserDetailService);
    }

    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() // httpbasic方式认证
        http.formLogin()  // http表单登录
            .loginPage(securityProperties.getAuthentication().getLoginPage()) // 默认登录页面, 请求 /login/page
            .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理Url, 默认是 /login
            .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) // 用户名-请求参数
            .passwordParameter(securityProperties.getAuthentication().getPasswordParameter()) // 密码-请求参数
            .successHandler(customerAuthenticationSuccessHandler)
            .and()
            .authorizeRequests() // 认证请求
            .antMatchers(securityProperties.getAuthentication().getLoginPage()).permitAll() // 拦截放行 /login/page 请求
            .anyRequest() // 所有通过http访问的请求都需要认证
            .authenticated()
        ;
    }

    /**
     * 放行静态资源请求(默认放在/resource/static目录下)
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
