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
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private static final String PREFIX_PATH = "system/role/";

    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "role-list";
    }

}
