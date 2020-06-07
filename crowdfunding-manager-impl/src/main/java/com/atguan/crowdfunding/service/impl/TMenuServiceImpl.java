package com.atguan.crowdfunding.service.impl;

import com.atguan.crowdfunding.bean.TMenu;
import com.atguan.crowdfunding.mapper.TMenuMapper;
import com.atguan.crowdfunding.service.TMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TMenuServiceImpl implements TMenuService {

    Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);

    @Autowired
    TMenuMapper menuMapper;

    @Override
    public List<TMenu> listMenuAll() {

        List<TMenu> menuList = new ArrayList<TMenu>();
        Map<Integer,TMenu> cache = new HashMap<Integer, TMenu>();

        List<TMenu> allList = menuMapper.selectByExample(null);

        //父菜单
        for (TMenu tMenu: allList) {
            if (tMenu.getPid() == 0) {
                menuList.add(tMenu);
                cache.put(tMenu.getId(),tMenu);
            }
        }

        //子菜单
        for (TMenu tMenu : allList) {
            if (tMenu.getPid() != 0) {
                Integer pid = tMenu.getPid();
                TMenu parent = cache.get(pid);
                parent.getChildren().add(tMenu);
            }
        }

        log.debug("菜单={}",menuList);

        return menuList;
    }

    @Override
    public List<TMenu> listMenuAllTree() {
        return menuMapper.selectByExample(null);
    }

    @Override
    public void saveTMenu(TMenu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public TMenu getMenuById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTMenu(TMenu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteTMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
