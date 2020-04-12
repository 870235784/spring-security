package com.tca.security.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoua
 * @Date 2020/4/12
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static final String PREFIX_PATH = "system/user/";

    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "user-list";
    }

}
