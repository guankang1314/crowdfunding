package com.atguan.crowdfunding.service.impl;


import com.atguan.crowdfunding.bean.TRole;
import com.atguan.crowdfunding.bean.TRoleExample;
import com.atguan.crowdfunding.mapper.TRoleMapper;
import com.atguan.crowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class TRoleServiceImpl implements TRoleService {

    @Autowired
    TRoleMapper roleMapper;

    @Override
    public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");

        List<TRole> list = null;

        if (StringUtils.isEmpty(condition)) {
            list = roleMapper.selectByExample(null);
        }else {

            TRoleExample example = new TRoleExample();
            example.createCriteria().andNameLike("%"+condition+"%");

            list = roleMapper.selectByExample(example);
        }

        PageInfo<TRole> page = new PageInfo<TRole>(list,5);

        return page;
    }

    @Override
    public void saveTRole(TRole role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public TRole getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void upadteTRole(TRole role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteTRole(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }
}
