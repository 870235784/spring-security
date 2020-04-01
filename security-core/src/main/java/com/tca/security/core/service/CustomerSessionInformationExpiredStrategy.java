package com.tca.security.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author zhoua
 * @Date 2020/4/1
 */
@Component("customerSessionInformationExpiredStrategy")
public class CustomerSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private CustomerAuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        // 1.获取失效的用户
        UserDetails userDetails = (UserDetails) event.getSessionInformation().getPrincipal();
        // 2.异常
        AuthenticationException authenticationException = new AuthenticationServiceException(String.format(
                "[%s], 当前用户已在另一台电脑登录, 您已被下线", userDetails.getUsername()));
        // 3.调用认证失败策略
        try {
            event.getRequest().setAttribute("toAuthentication", true);
            customerAuthenticationFailureHandler.onAuthenticationFailure(event.getRequest(),
                    event.getResponse(), authenticationException);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
