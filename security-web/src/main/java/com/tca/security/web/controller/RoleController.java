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
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private static final String PREFIX_PATH = "system/role/";

    /**
     * 跳转到角色管理页面  role-list.html
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role')")
    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "role-list";
    }

    /**
     * 跳转到 role-form.html 新增/编辑
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add', 'sys:role:edit', 'sys:role:list', 'sys:role:delete')")
    @GetMapping("/form")
    public String form() {
        return PREFIX_PATH + "role-form";
    }

}
