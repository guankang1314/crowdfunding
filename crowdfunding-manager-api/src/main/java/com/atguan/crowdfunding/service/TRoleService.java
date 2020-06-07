package com.atguan.crowdfunding.service;


import com.atguan.crowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TRoleService {
    PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

    void saveTRole(TRole role);

    TRole getRoleById(Integer id);

    void upadteTRole(TRole role);

    void deleteTRole(Integer id);
}
