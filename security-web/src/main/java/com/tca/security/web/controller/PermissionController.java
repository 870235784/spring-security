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
@RequestMapping("/permission")
@Slf4j
public class PermissionController {

    private static final String PREFIX_PATH = "system/permission/";

    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "permission-list";
    }

}
