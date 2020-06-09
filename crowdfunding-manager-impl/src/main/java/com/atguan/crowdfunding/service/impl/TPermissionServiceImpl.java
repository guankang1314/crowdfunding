package com.atguan.crowdfunding.service.impl;

import com.atguan.crowdfunding.bean.TPermission;
import com.atguan.crowdfunding.mapper.TPermissionMapper;
import com.atguan.crowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TPermissionServiceImpl implements TPermissionService {

    @Autowired
    TPermissionMapper permissionMapper;

    @Override
    public List<TPermission> getAllPermissions() {

        List<TPermission> permissionList = new ArrayList<TPermission>();
        Map<Integer,TPermission> cache = new HashMap<Integer, TPermission>();

        List<TPermission> allList = permissionMapper.selectByExample(null);

        for (TPermission permission : allList) {
            if (permission.getPid()==0) {
                permissionList.add(permission);
                cache.put(permission.getId(),permission);
            }
        }

        for (TPermission permission : allList) {
            if (permission.getPid()!=0) {
                Integer pid = permission.getPid();
                TPermission parent = cache.get(pid);
                parent.getChildren().add(permission);
            }
        }


        return permissionList;
    }

    @Override
    public void savePermission(TPermission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void deletePermission(Integer id) {

        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void editPermission(TPermission permission) {

        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public TPermission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }
}
