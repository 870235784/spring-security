package com.tca.security.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author zhoua
 * @Date 2020/3/20
 */
@Controller
public class LoginController {

    /**
     * 跳转到 resource/templates/index.html
     * 注意: Controller上使用@Controller, 不能使用@RestController
     * @return
     */
    @RequestMapping("/index")
    public String index(Map<String, Object> map) {
        // 方式1: 获取登录用户信息
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            map.put("username", userDetails.getUsername());
        }
        return "index";
    }

    /**
     * 跳转到 resource/templates/login.html
     * @return
     */
    @RequestMapping("/login/page")
    public String loginPage() {
        return "login";
    }

    /**
     * 方式二: 获取用户信息
     * @param authentication
     * @return
     */
    @RequestMapping("/user/info")
    @ResponseBody
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
