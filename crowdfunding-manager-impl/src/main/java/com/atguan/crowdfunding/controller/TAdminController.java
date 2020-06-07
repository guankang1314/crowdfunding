package com.atguan.crowdfunding.controller;


import com.atguan.crowdfunding.bean.TAdmin;
import com.atguan.crowdfunding.service.TAmdinService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TAdminController {

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
}
