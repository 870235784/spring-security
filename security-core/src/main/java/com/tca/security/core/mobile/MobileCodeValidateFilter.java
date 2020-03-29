package com.tca.security.core.mobile;

import com.tca.security.core.constant.SecurityConstants;
import com.tca.security.core.exception.ImageCodeException;
import com.tca.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoua
 * @Date 2020/3/26
 * 手机验证码过滤器
 */
@Component("mobileCodeValidateFilter")
public class MobileCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.满足条件的url需要校验
        if ("/mobile/form".equals(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            try {
                validateMobileCode(request);
            } catch (ImageCodeException exception) {
                customerAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        // 2.放行
        filterChain.doFilter(request, response);
    }

    /**
     * 校验手机验证码
     * @param request
     */
    private void validateMobileCode(HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute(SecurityConstants.MOBILE_CODE_SESSION_ID);
        String inputCode = request.getParameter("code");
        if (ValidateUtils.isEmpty(inputCode)) {
            throw new ImageCodeException("请输入验证码");
        }
        if (!inputCode.equalsIgnoreCase(code)) {
            throw new ImageCodeException("验证码输入错误");
        }
    }
}
