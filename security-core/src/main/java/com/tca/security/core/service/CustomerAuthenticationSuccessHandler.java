package com.tca.security.core.service;

import com.alibaba.fastjson.JSONObject;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.enums.LoginResponseType;
import com.tca.security.core.properties.SecurityProperties;
import com.tca.utils.ValidateUtils;
import com.tca.utils.WebBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证成功处理器
 * @author zhoua
 * @Date 2020/3/25
 */
@Service("customerAuthenticationSuccessHandler")
@Slf4j
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
        // 重新加载权限菜单
        reloadPermissionMenu(authentication.getPrincipal());

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

    /**
     * 重新加载权限菜单
     * @param principal
     */
    private void reloadPermissionMenu(Object principal) {
        if (ValidateUtils.isNotEmpty(principal) && principal instanceof SysUser) {
            SysUser sysUser = (SysUser) principal;
            // 1.获取之前的所有权限
            List<SysPermission> sysPermissionList = sysUser.getPermissions();
            if (ValidateUtils.isEmpty(sysPermissionList)) {
                return;
            }
            // 2.重新封装权限
            // 2.1 找出所有菜单(type == 1)
            List<SysPermission> menuList = sysPermissionList.stream().filter(sysPermission ->
                    sysPermission.getType().equals(1)).collect(Collectors.toList());
            if (ValidateUtils.isEmpty(menuList)) {
                return;
            }
            // 2.2 将子菜单放到父菜单下
            menuList.forEach(menu -> {
                List<SysPermission> childrenMenu = menuList.stream().filter(menuParam -> menuParam.getParentId().equals(menu.getId()))
                        .distinct().collect(Collectors.toList());
                menu.setChildren(ValidateUtils.isEmpty(childrenMenu)? Lists.newArrayList(): childrenMenu);
                menu.setChildrenUrl(ValidateUtils.isEmpty(childrenMenu)? Lists.newArrayList():
                        childrenMenu.stream().map(SysPermission::getUrl).distinct().collect(Collectors.toList()));
            });
            // 2.3 只取所有父菜单
            sysUser.setPermissions(menuList.stream().filter(sysPermission ->
                    sysPermission.getParentId().equals(0L)).collect(Collectors.toList()));
            log.info("重载权限后: user = {}", sysUser);
        }
    }
}
