package com.tca.security.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.beans.ReturnBaseResultBean;
import com.tca.security.core.auth.entity.SysRole;
import com.tca.security.core.auth.service.ISysRoleService;
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
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private static final String PREFIX_PATH = "system/role/";

    @Autowired
    private ISysRoleService sysRoleService;

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
     * 跳转到 role-form.html 新增
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:add')")
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("role", new SysRole());
        return PREFIX_PATH + "role-form";
    }

    /**
     * 跳转到 role-form.html 编辑
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @GetMapping(value = {"/form/{id}"})
    public String form(@PathVariable(value = "id") Long id, Model model) {
        SysRole sysRole = sysRoleService.getById(id, true);
        model.addAttribute("role", sysRole);
        return PREFIX_PATH + "role-form";
    }

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role:list', 'sys:role:page')")
    @PostMapping("/page")
    @ResponseBody
    public ReturnBaseResultBean<SysRole> page(Page<SysRole> page, SysRole sysRole) {
        IPage<SysRole> sysRolePage = sysRoleService.page(page, sysRole);
        ReturnBaseResultBean<SysRole> returnBaseResultBean = new ReturnBaseResultBean<>();
        returnBaseResultBean.setRows(sysRolePage.getRecords());
        returnBaseResultBean.setTotal((int)sysRolePage.getTotal());
        WebBaseUtils.setReturnBaseMessage(returnBaseResultBean, ErrorCode.S0000);
        return returnBaseResultBean;
    }

    /**
     * 提交新增的数据
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:add')")
    @RequestMapping(method = {RequestMethod.POST})
    public String insert(SysRole role) {
        sysRoleService.insert(role);
        return "redirect:/role";
    }

    /**
     * 提交更新的数据
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @RequestMapping(method = {RequestMethod.PUT})
    public String update(SysRole role) {
        sysRoleService.update(role);
        return "redirect:/role";
    }

    /**
     * 删除 role
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnBaseMessageBean delete(@PathVariable(value = "id") Long id) {
        ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
        boolean result = sysRoleService.delete(id);
        if (result) {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S0000);
        } else {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S9999);
        }
        return returnBaseMessageBean;
    }


}
