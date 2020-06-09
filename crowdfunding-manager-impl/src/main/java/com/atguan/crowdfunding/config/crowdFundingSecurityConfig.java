package com.atguan.crowdfunding.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class crowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/static/**","/welcome.jsp","/tologin").permitAll()
                .anyRequest().authenticated();//剩下都需要认证

// /login.jsp==POST  用户登陆请求发给Security
        http.formLogin().loginPage("/tologin")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main").permitAll();

        http.csrf().disable();

        http.logout().logoutSuccessUrl("/index");

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

                //httpServletResponse.getWriter().print("403");

                String type = httpServletRequest.getHeader("X-Requested-With");

                if ("XMLHttpRequest".equals(type)) {
                    httpServletResponse.getWriter().print("403");
                }else {
                    httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/ERROR/error403.jsp").forward(httpServletRequest,httpServletResponse);

                }

            }
        });



        http.rememberMe();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
