package com.tca.security.core.config;

import com.tca.security.core.authorize.AuthorizeConfigurerManager;
import com.tca.security.core.imageCode.ImageCodeValidateFilter;
import com.tca.security.core.mobile.MobileAuthenticationConfig;
import com.tca.security.core.mobile.MobileCodeValidateFilter;
import com.tca.security.core.properties.SecurityProperties;
import com.tca.security.core.service.CustomerLogoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @author zhoua
 * @Date 2020/3/22
 * spring-security 核心配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService customerUserDetailService;

    @Autowired
    private AuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private MobileCodeValidateFilter mobileCodeValidateFilter;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private CustomerLogoutHandler customerLogoutHandler;

    @Autowired
    private AuthorizeConfigurerManager authorizeConfigurerManager;

    @Autowired
    private DataSource dataSource;

    /**
     * 加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * session注册管理器
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 实现remember me功能
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 项目启动时创建数据库表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
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
        http
            // 认证配置
            .addFilterBefore(mobileCodeValidateFilter, UsernamePasswordAuthenticationFilter.class) // 手机验证码过滤器
            .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class) // 图形验证码过滤器
            .formLogin()  // http表单登录
            .loginPage(securityProperties.getAuthentication().getLoginPage()) // 默认登录页面, 请求 /login/page
            .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理Url, 默认是 /login
            .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) // 用户名-请求参数
            .passwordParameter(securityProperties.getAuthentication().getPasswordParameter()) // 密码-请求参数
            .successHandler(customerAuthenticationSuccessHandler) // 认证成功处理器
            .failureHandler(customerAuthenticationFailureHandler) // 认证失败处理器

            // 鉴权配置
            /*.and()
            .authorizeRequests() // 授权请求
            .antMatchers(securityProperties.getAuthentication().getIgnoreUrls()).permitAll() // permitAll: 满足当前url条件放行
            .antMatchers("/user").hasAuthority("sys:user") // hasAuthority: 拥有sys:user权限的用户, 可以访问任意请求方式的/user
            .antMatchers(HttpMethod.GET, "/role").hasAnyAuthority("sys:role") // 拥有sys:role权限的用户, 可以访问GET请求方式的/user
            // 表示拥有 sys:permission 权限的用户 或者 拥有 ROLE_ADMIN 角色的用户 可以访问GET请求方式的 /permission (注意:角色会加上前缀 ROLE_，即真实是 ROLE_ADMIN)
            .antMatchers(HttpMethod.GET, "/permission").access("hasAuthority('sys:permission') or hasAnyRole('ADMIN')")
            .anyRequest() // 所有通过http访问的请求都需要认证
            .authenticated()*/

            // remember-me配置
            .and()
            .rememberMe() // 记住我
            .tokenRepository(jdbcTokenRepository())
            .tokenValiditySeconds(60 * 60 * 24 * 7)

            // session配置
            .and()
            .sessionManagement()
            .invalidSessionStrategy(invalidSessionStrategy) // session失效策略
            .maximumSessions(1) // 同一用户session最大数
            .expiredSessionStrategy(sessionInformationExpiredStrategy)
//            .maxSessionsPreventsLogin(true) // 一般不推荐打开,达到session最大数时阻止新增新的session
            .sessionRegistry(sessionRegistry())

            // 登出配置
            .and().and().logout()
            .addLogoutHandler(customerLogoutHandler)
        ;
        // 鉴权配置
        authorizeConfigurerManager.configure(http.authorizeRequests());
        http.csrf().disable(); // 关闭csrf攻击防护
        http.apply(mobileAuthenticationConfig);
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
