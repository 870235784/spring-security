package com.tca.security.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhoua
 * @Date 2020/3/22
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 用户名
     */
    private static final String USERNAME = "admin";

    /**
     * 密码
     */
    private static final String PASSWORD = "123456";

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
        auth.inMemoryAuthentication()
                .withUser(USERNAME)
                .password(passwordEncoder().encode(PASSWORD))
                .authorities("ADMIN");
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
            .loginPage("/login/page") // 默认登录页面, 请求 /login/page
            .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login
            .usernameParameter("username") // 用户名-请求参数
            .passwordParameter("password") // 密码-请求参数
            .and()
            .authorizeRequests() // 认证请求
            .antMatchers("/login/page").permitAll() // 拦截放行 /login/page 请求
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
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }
}
