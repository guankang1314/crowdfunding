package com.atguan.crowdfunding.listener;

import com.atguan.crowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemUpInitListener implements ServletContextListener {

    Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);

    //监听初始化

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        log.debug("当前上下文路径:{}",contextPath);
        application.setAttribute(Const.PATH,contextPath);
        System.out.println("当前上下文路径："+contextPath);
    }

    //监听销毁

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        log.debug("当前application对象已销毁。");
    }
}
