package com.tca.security.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.beans.ReturnBaseResultBean;
import com.tca.security.core.auth.entity.SysUser;
import com.tca.security.core.auth.service.ISysRoleService;
import com.tca.security.core.auth.service.ISysUserService;
import com.tca.utils.WebBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhoua
 * @Date 2020/4/12
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    private static final String PREFIX_PATH = "system/user/";

    /**
     * 跳转到用户管理页面 user-list.html
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:user')")
    @GetMapping({"/", ""})
    public String user() {
        return PREFIX_PATH + "user-list";
    }

    /**
     * 跳转到 role-form.html 新增
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:add')")
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("user", new SysUser());
        model.addAttribute("roleList", sysRoleService.list());
        return PREFIX_PATH + "user-form";
    }

    /**
     * 跳转到 user-form.html 编辑
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value = {"/form/{id}"})
    public String form(@PathVariable(value = "id") Long id, Model model) {
        SysUser sysUser = sysUserService.getById(id, true);
        model.addAttribute("user", sysUser);
        model.addAttribute("roleList", sysRoleService.list());
        return PREFIX_PATH + "user-form";
    }


    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:user:list', 'sys:user:page')")
    @PostMapping("/page")
    @ResponseBody
    public ReturnBaseResultBean<SysUser> page(Page<SysUser> page, SysUser sysUser) {
        IPage<SysUser> sysUserPage = sysUserService.page(page, sysUser);
        ReturnBaseResultBean<SysUser> returnBaseResultBean = new ReturnBaseResultBean<>();
        returnBaseResultBean.setRows(sysUserPage.getRecords());
        returnBaseResultBean.setTotal((int)sysUserPage.getTotal());
        WebBaseUtils.setReturnBaseMessage(returnBaseResultBean, ErrorCode.S0000);
        return returnBaseResultBean;
    }

    /**
     * 提交新增的数据
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:add')")
    @RequestMapping(method = {RequestMethod.POST})
    public String insert(SysUser user) {
        sysUserService.insert(user);
        return "redirect:/user";
    }

    /**
     * 提交更新的数据
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @RequestMapping(method = {RequestMethod.PUT})
    public String update(SysUser user) {
        sysUserService.update(user);
        return "redirect:/user";
    }

    /**
     * 删除 user
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnBaseMessageBean delete(@PathVariable(value = "id") Long id) {
        ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
        boolean result = sysUserService.delete(id);
        if (result) {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S0000);
        } else {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S9999);
        }
        return returnBaseMessageBean;
    }


}
