package com.web.app.security.jwt.configurers;

import com.web.app.security.jwt.filters.JwtFilter;
import com.web.app.security.jwt.providers.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider JwtProvider;

    public JwtConfigurer(JwtProvider JwtProvider) {
        this.JwtProvider = JwtProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        /*  TODO:
             1. Не выкупаю - почему мы пишем new JwtFilter(JwtProvider) - ?, почему не
                @Autowired JwtFilter genericFilterBean (автоваерд по классу, чтоб не мучаться с квалифаерами если что)
                genericFilterBean вроде как будет сконфишурировани и все будет ок? */
        GenericFilterBean genericFilterBean = new JwtFilter(JwtProvider);
        httpSecurity.addFilterBefore(genericFilterBean, UsernamePasswordAuthenticationFilter.class);
    }
}
