package com.tca.security.core.service;

import com.alibaba.fastjson.JSONObject;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.security.core.enums.LoginResponseType;
import com.tca.security.core.properties.SecurityProperties;
import com.tca.utils.WebBaseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("customerAuthenticationFailureHandler")
//public class CustomerAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证失败的处理
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginResponseType = securityProperties.getAuthentication().getLoginResponseType();
        // 如果使用json的方式
        if (LoginResponseType.JSON.name().equals(loginResponseType)) {
            ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
            if (request.getAttribute("toAuthentication") != null) {
                WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.B5006);
            } else {
                WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.B5004);
            }
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(returnBaseMessageBean));
        } else if (LoginResponseType.REDIRECT.name().equals(loginResponseType)) {
            // 使用重定向的方式
//            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage() + "?error");
            String lastUrl = request.getAttribute("toAuthentication") == null?
                StringUtils.substringBeforeLast(request.getHeader("referer"), "?"): securityProperties
                    .getAuthentication().getLoginPage();
            super.setDefaultFailureUrl(lastUrl + "?error");
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
