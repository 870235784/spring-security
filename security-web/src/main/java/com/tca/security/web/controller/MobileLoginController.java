package com.tca.security.web.controller;

import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.security.core.constant.SecurityConstants;
import com.tca.security.core.mobile.IsmsSender;
import com.tca.utils.WebBaseUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoua
 * @Date 2020/3/29
 */
@Controller
public class MobileLoginController {

    @Autowired
    private IsmsSender smsSender;

    /**
     * 跳转到手机登录页面
     * @return
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }

    /**
     * 发送手机验证码
     * @param request
     * @return
     */
    @RequestMapping("/code/mobile")
    @ResponseBody
    public ReturnBaseMessageBean smsCode(HttpServletRequest request) {
        // 1.生成手机验证码
        String code = RandomStringUtils.randomNumeric(4);
        // 2.code放入session
        request.getSession().setAttribute(SecurityConstants.MOBILE_CODE_SESSION_ID, code);
        // 3.发送短信
        smsSender.sendValidateCode(request.getParameter("mobile"), code);
        ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
        WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S0000);
        return returnBaseMessageBean;
    }
}
