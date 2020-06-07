package com.atguan.crowdfunding.controller;


import com.atguan.crowdfunding.bean.TAdmin;
import com.atguan.crowdfunding.bean.TMenu;
import com.atguan.crowdfunding.service.TAmdinService;
import com.atguan.crowdfunding.service.TMenuService;
import com.atguan.crowdfunding.util.Const;
import org.apache.ibatis.binding.MapperMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DispatchController {


    @Autowired
    TAmdinService amdinService;

    @Autowired
    TMenuService menuService ;

    Logger log = LoggerFactory.getLogger(DispatchController.class);

    @RequestMapping("/index")
    public String index() {
        System.out.println("跳转系统主页面..");
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("跳转登录主页面..");
        return "login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session) {
        System.out.println("跳转后台主页面..");

        if (session==null) {
            return "redirect:/login";
        }
        //存放父菜单
        List<TMenu> menuList = (List<TMenu>)session.getAttribute("menuList");

        if (menuList==null) {
            menuList = menuService.listMenuAll();
            session.setAttribute("menuList",menuList);
        }
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("注销系统..");

        if (session!=null) {
            session.removeAttribute(Const.LOGIN_ADMIN);
            session.invalidate();
        }

        return "redirect:/index";
    }

    @RequestMapping("/dologin")
    public String dologin(String loginacct, String userpswd, HttpSession session, Model model) {
        log.debug("开始denlu");

        log.debug("loginacct={}",loginacct);
        log.debug("userpswd={}",userpswd);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("loginacct",loginacct);
        paramMap.put("userpswd",userpswd);
        try {
            TAdmin admin = amdinService.getTAdminByLogin(paramMap);
            session.setAttribute(Const.LOGIN_ADMIN,admin);
            log.debug("登录成功..");
            //return "main";
            return "redirect:/main";
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("登陆失败={}..",e.getMessage());
            model.addAttribute("message",e.getMessage());
            return "login";
        }


    }

}
