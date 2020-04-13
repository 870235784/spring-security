package com.tca.security.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * 跳转到用户管理页面 user-list.html
     * @return
     */
    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "user-list";
    }

    /**
     * 跳转到 user-form.html 新增/编辑用户
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @GetMapping("form")
    public String form() {
        return PREFIX_PATH + "user-form";
    }

}
