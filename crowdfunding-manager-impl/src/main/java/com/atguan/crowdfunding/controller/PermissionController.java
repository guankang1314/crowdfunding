package com.atguan.crowdfunding.controller;


import com.atguan.crowdfunding.bean.TPermission;
import com.atguan.crowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    TPermissionService permissionService;

    @GetMapping("/index")
    public String index() {
        return "permission/index";
    }

    @ResponseBody
    @GetMapping("/listAllPermissionTree")
    public List<TPermission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @ResponseBody
    @PostMapping("/add")
    public String addPermission(TPermission permission) {
        permissionService.savePermission(permission);
        return "ok";
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public String deletePermission(Integer id) {
        permissionService.deletePermission(id);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/edit")
    public String editPermission(TPermission permission) {
        permissionService.editPermission(permission);
        return "ok";
    }

    @ResponseBody
    @GetMapping("/get")
    public TPermission getPermission(Integer id) {
        return permissionService.getPermissionById(id);
    }

}
