package com.atguan.crowdfunding.service.impl;

import com.atguan.crowdfunding.bean.TAdmin;
import com.atguan.crowdfunding.bean.TAdminExample;
import com.atguan.crowdfunding.exception.LoginException;
import com.atguan.crowdfunding.mapper.TAdminMapper;
import com.atguan.crowdfunding.service.TAmdinService;
import com.atguan.crowdfunding.util.AppDateUtils;
import com.atguan.crowdfunding.util.Const;
import com.atguan.crowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public  class TAmdinServiceimpl implements TAmdinService {

    @Autowired
    TAdminMapper adminMapper;


    @Override
    public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
        //密码加密

        String loginacct = (String) paramMap.get("loginacct");
        String userpswd = (String) paramMap.get("userpswd");
        //查询用户
        TAdminExample example = new TAdminExample();

        example.createCriteria().andLoginacctEqualTo(loginacct);

        List<TAdmin> list = adminMapper.selectByExample(example);
        //判断用户是否为null

        if (list!=null && list.size()==1) {
            TAdmin admin = list.get(0);
            //判断密码是否为null

            if (admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
                //返回结果
                return admin;
            }else {
                throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
            }
        }else {
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }
    }

    @Override
    public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");

        TAdminExample example = new TAdminExample();

        //模糊查询
        if (!"".equals(condition)) {
            example.createCriteria().andLoginacctLike("%"+condition+"%");

            TAdminExample.Criteria criteria2 = example.createCriteria();
            criteria2.andUsernameLike("%"+condition+"%");

            TAdminExample.Criteria criteria3 = example.createCriteria();
            criteria3.andEmailLike("%"+condition+"%");

            example.or(criteria2);
            example.or(criteria3);
        }

        //example.setOrderByClause("createtime desc");

        List<TAdmin> list = adminMapper.selectByExample(example);

        PageInfo<TAdmin> page = new PageInfo<>(list,5);

        return page;
    }

    @Override
    public void saveTAdmin(TAdmin admin) {

        admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));

        admin.setCreatetime(AppDateUtils.getFormatTime());

        adminMapper.insertSelective(admin);
    }

    @Override
    public TAdmin getTAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTAdmin(TAdmin admin) {

        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void deleteTAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        adminMapper.deleteBatch(idList);
    }
}
