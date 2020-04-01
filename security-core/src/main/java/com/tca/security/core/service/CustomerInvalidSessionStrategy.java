package com.tca.security.core.service;

import com.alibaba.fastjson.JSONObject;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.utils.WebBaseUtils;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoua
 * @Date 2020/3/31
 */
@Component
public class CustomerInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
    // 将浏览器的sessionid清除，不关闭浏览器cookie不会被删除，一直请求都提示：Session失效
        cancelCookie(request, response);
        ReturnBaseMessageBean result = new ReturnBaseMessageBean();
        WebBaseUtils.setReturnBaseMessage(result, ErrorCode.B5005);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
