package com.tca.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoua
 * @Date 2020/3/20
 */
@Controller
public class DispatcherController {

    /**
     * 跳转到 resource/templates/index.html
     * 注意: Controller上使用@Controller, 不能使用@RestController
     * @return
     */
    @RequestMapping("/index")
    public String index() {
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
}
