package com.tca.security.core.service;

import com.alibaba.fastjson.JSONObject;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.security.core.enums.LoginResponseType;
import com.tca.security.core.properties.SecurityProperties;
import com.tca.utils.WebBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("customerAuthenticationSuccessHandler")
//public class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
public class CustomerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证成功的处理
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String loginResponseType = securityProperties.getAuthentication().getLoginResponseType();
        // 使用json方式
        if (LoginResponseType.JSON.name().equals(loginResponseType)) {
            ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S0000);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(returnBaseMessageBean));
        } else if (LoginResponseType.REDIRECT.name().equals(loginResponseType)) {
            // 使用重定向的方式
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
