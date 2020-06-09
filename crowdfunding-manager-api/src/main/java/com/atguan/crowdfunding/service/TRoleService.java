package com.atguan.crowdfunding.service;


import com.atguan.crowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TRoleService {
    PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

    void saveTRole(TRole role);

    TRole getRoleById(Integer id);

    void upadteTRole(TRole role);

    void deleteTRole(Integer id);

    List<TRole> listAllRole();

    List<Integer> getRoleIdByAdminId(String id);

    void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId);

    void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId);

    void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids);

    List<Integer> listPermissionIdByRoleId(Integer roleId);
}
