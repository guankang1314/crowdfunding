package com.atguan.crowdfunding.controller;


import com.atguan.crowdfunding.bean.TMenu;
import com.atguan.crowdfunding.service.TMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TMenuController {


    @Autowired
    TMenuService menuService;

    @RequestMapping("/menu/index")
    public String index() {
        return "menu/index";
    }


    @ResponseBody
    @RequestMapping("/menu/doAdd")
    public String doAdd(TMenu menu) {
        menuService.saveTMenu(menu);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/menu/loadTree")
    public List<TMenu> loadTree() {

        return menuService.listMenuAllTree();
    }


    @ResponseBody
    @RequestMapping("/menu/getMenuById")
    public TMenu getMenuById(Integer id) {
        TMenu menu = menuService.getMenuById(id);
        return menu;
    }


    @ResponseBody
    @RequestMapping("/menu/doUpdate")
    public String doUpdate(TMenu menu) {
        menuService.updateTMenu(menu);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("//menu/doDelete")
    public String doDelete(Integer id) {
        menuService.deleteTMenu(id);
        return "ok";
    }
}
