package com.atguan.crowdfunding.service;

import com.atguan.crowdfunding.bean.TAdmin;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TAmdinService  {
    TAdmin getTAdminByLogin(Map<String, Object> paramMap);

    PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);

    void saveTAdmin(TAdmin admin);

    TAdmin getTAdminById(Integer id);

    void updateTAdmin(TAdmin admin);

    void deleteTAdmin(Integer id);

    void deleteBatch(List<Integer> idList);
}
