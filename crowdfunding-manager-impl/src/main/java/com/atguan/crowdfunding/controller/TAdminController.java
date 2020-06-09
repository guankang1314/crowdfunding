package com.atguan.crowdfunding.controller;


import com.atguan.crowdfunding.bean.TAdmin;
import com.atguan.crowdfunding.bean.TRole;
import com.atguan.crowdfunding.service.TAmdinService;
import com.atguan.crowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TAdminController {

    @Autowired
    TRoleService roleService;

    @Autowired
    TAmdinService amdinService;

    Logger log = LoggerFactory.getLogger(TAdminController.class);

    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
                        Model model,
                        @RequestParam(value = "condition",required = false,defaultValue = "") String condition) {

        log.debug("pageNum={}",pageNum);
        log.debug("pageSize={}",pageSize);
        log.debug("condition",condition);

        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("condition",condition);

        PageInfo<TAdmin> page = amdinService.listAdminPage(paramMap);

        model.addAttribute("page",page);

        return "admin/index";
    }

    @RequestMapping("/admin/toAdd")
    public String toAdd() {
        return "admin/add";
    }

    @RequestMapping("/admin/doAdd")
    public String doAdd(TAdmin admin) {

        amdinService.saveTAdmin(admin);

        return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/toUpdate")
    public String toUpdate(Integer id,Model model) {

        TAdmin admin = amdinService.getTAdminById(id);
        model.addAttribute("admin",admin);

        return "admin/update";
    }

    @RequestMapping("/admin/doUpdate")
    public String doUpdate(TAdmin admin,Integer pageNum) {

        amdinService.updateTAdmin(admin);

        return "redirect:/admin/index?pageNum="+pageNum;
    }

    @RequestMapping("/admin/doDelete")
    public String doDelete(Integer id,Integer pageNum) {

        amdinService.deleteTAdmin(id);

        return "redirect:/admin/index?pageNum="+pageNum;
    }

    @RequestMapping("/admin/deDeleteBatch")
    public String deDeleteBatch(String ids,Integer pageNum) {
        List<Integer> idList = new ArrayList<Integer>();

        String[] split = ids.split(",");

        for (String idStr : split) {
            int id = Integer.parseInt(idStr);
            idList.add(id);
        }

        amdinService.deleteBatch(idList);

        return "redirect:/admin/index?pageNum="+pageNum;
    }


    @RequestMapping("/admin/toAssign")
    public String toAssign(String id,Model model) {

        //查询所有角色
        List<TRole> allList = roleService.listAllRole();

        //根据用户id查询已有id
        List<Integer> roleIdList = roleService.getRoleIdByAdminId(id);

        //将所有角色进行划分
        List<TRole> assignList = new ArrayList<TRole>();
        List<TRole> unAssignList = new ArrayList<TRole>();

        for (TRole role : allList) {
            if (roleIdList.contains(role.getId())) {
                //已有
                assignList.add(role);
            }else {
                //未存在
                unAssignList.add(role);
            }
        }

        model.addAttribute("assignList",assignList);
        model.addAttribute("unAssignList",unAssignList);


        return "admin/assignRole";
    }

    @ResponseBody
    @RequestMapping("/admin/doAssign")
    public String doAssign(Integer[] roleId,Integer adminId) {

        log.debug("adminId={}",adminId);
        for (Integer rId : roleId) {
            log.debug("roleId={}",rId);
        }

        roleService.saveAdminAndRoleRelationship(roleId,adminId);

        return "ok";
    }


    @ResponseBody
    @RequestMapping("/admin/doUnAssign")
    public String doUnAssign(Integer[] roleId,Integer adminId) {

        log.debug("adminId={}",adminId);
        for (Integer rId : roleId) {
            log.debug("roleId={}",rId);
        }

        roleService.deleteAdminAndRoleRelationship(roleId,adminId);

        return "ok";
    }

}
