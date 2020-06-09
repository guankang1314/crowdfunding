package com.atguan.crowdfunding.component;

import com.atguan.crowdfunding.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class TSecurityAdmin extends User {

    TAdmin admin;

    public TSecurityAdmin(TAdmin admin, List<GrantedAuthority> authorities) {

        //super(admin.getLoginacct(),admin.getUserpswd(),authorities);

        super(admin.getLoginacct(),admin.getUserpswd(),true,true,true,true,authorities);
        this.admin = admin;
    }
}
