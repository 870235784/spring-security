package com.tca.security.web.controller;

import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.beans.ReturnBaseResultBean;
import com.tca.security.core.auth.entity.SysPermission;
import com.tca.security.core.auth.service.ISysPermissionService;
import com.tca.utils.WebBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhoua
 * @Date 2020/4/12
 */
@Controller
@RequestMapping("/permission")
@Slf4j
public class PermissionController {

    @Autowired
    private ISysPermissionService sysPermissionService;

    private static final String PREFIX_PATH = "system/permission/";



    /**
     * 跳转到权限管理页面  permission-list.html
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission')")
    @GetMapping({"/", ""})
    public String permission() {
        return PREFIX_PATH + "permission-list";
    }

    /**
     * 查看列表
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:list')")
    @PostMapping("/list")
    @ResponseBody
    public ReturnBaseResultBean<SysPermission> list() {
        ReturnBaseResultBean<SysPermission> returnBaseResultBean = new ReturnBaseResultBean<>();
        List<SysPermission> sysPermissionList = sysPermissionService.list();
        returnBaseResultBean.setRows(sysPermissionList);
        WebBaseUtils.setReturnBaseMessage(returnBaseResultBean, ErrorCode.S0000);
        return returnBaseResultBean;
    }


    /**
     * 跳转到 permission-form.html 新增
     * @return
     */
    @PreAuthorize("hasAuthority('sys:permission:add')")
    @GetMapping(value = {"/form"})
    public String form(Model model) {
        model.addAttribute("permission", new SysPermission());
        return PREFIX_PATH + "permission-form";
    }

    /**
     * 跳转到 permission-form.html 编辑
     * @return
     */
    @PreAuthorize("hasAuthority('sys:permission:edit')")
    @GetMapping(value = {"/form/{id}"})
    public String form(@PathVariable(value = "id", required = false) Long id, Model model) {
        SysPermission sysPermission = sysPermissionService.getById(id);
        model.addAttribute("permission", sysPermission);
        return PREFIX_PATH + "permission-form";
    }


    /**
     * 提交新增或修改的数据
     * @param permission
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:edit', 'sys:permission:add')")
    @RequestMapping(value="", method = {RequestMethod.PUT, RequestMethod.POST}) // /permission
    public String saveOrUpdate(SysPermission permission) {
        sysPermissionService.saveOrUpdate(permission);
        return "redirect:/permission";
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnBaseMessageBean delete(@PathVariable(value = "id") Long id) {
        ReturnBaseMessageBean returnBaseMessageBean = new ReturnBaseMessageBean();
        boolean result = sysPermissionService.delete(id);
        if (result) {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S0000);
        } else {
            WebBaseUtils.setReturnBaseMessage(returnBaseMessageBean, ErrorCode.S9999);
        }
        return returnBaseMessageBean;
    }


}
