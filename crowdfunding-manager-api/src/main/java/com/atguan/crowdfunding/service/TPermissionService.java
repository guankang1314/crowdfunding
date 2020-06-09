package com.atguan.crowdfunding.service;

import com.atguan.crowdfunding.bean.TPermission;

import java.util.List;

public interface TPermissionService {
    List<TPermission> getAllPermissions();

    void savePermission(TPermission permission);

    void deletePermission(Integer id);

    void editPermission(TPermission permission);

    TPermission getPermissionById(Integer id);
}
