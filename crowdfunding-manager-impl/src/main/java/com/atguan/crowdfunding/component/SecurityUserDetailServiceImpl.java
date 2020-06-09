package com.atguan.crowdfunding.component;

import com.atguan.crowdfunding.bean.TAdmin;
import com.atguan.crowdfunding.bean.TAdminExample;
import com.atguan.crowdfunding.bean.TPermission;
import com.atguan.crowdfunding.bean.TRole;
import com.atguan.crowdfunding.mapper.TAdminMapper;
import com.atguan.crowdfunding.mapper.TPermissionMapper;
import com.atguan.crowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    TAdminMapper adminMapper;


    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TPermissionMapper permissionMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(s);
        List<TAdmin> list = adminMapper.selectByExample(example);

        if (list!=null && list.size()==1) {

            TAdmin admin = list.get(0);

            Integer adminId = admin.getId();

            List<TRole> roleList = roleMapper.listRoleByAdminId(adminId);

            List<TPermission> permissionList = permissionMapper.listPermissionByAdminId(adminId);

            List<GrantedAuthority> authorities = new ArrayList<>();

            for (TRole role:roleList) {


                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }


            for (TPermission permission:permissionList) {


                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }


            //return new User(admin.getLoginacct(),admin.getUserpswd(),authorities);

            return new TSecurityAdmin(admin,authorities);
        }else {
            return null;
        }

    }
}
