package com.atguan.crowdfunding.service;

import com.atguan.crowdfunding.bean.TMenu;

import java.util.List;

public interface TMenuService {

    List<TMenu> listMenuAll();

    List<TMenu> listMenuAllTree();

    void saveTMenu(TMenu menu);

    TMenu getMenuById(Integer id);

    void updateTMenu(TMenu menu);

    void deleteTMenu(Integer id);
}
