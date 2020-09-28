/*
package com.web.app.security.jwt.configurers;

import com.web.app.security.jwt.filters.JwtFilter;
import com.web.app.security.jwt.providers.JwtProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider JwtProvider;

    public JwtConfigurer(JwtProvider JwtProvider) {
        this.JwtProvider = JwtProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        GenericFilterBean genericFilterBean = new JwtFilter(JwtProvider);
        httpSecurity.addFilterBefore(genericFilterBean, UsernamePasswordAuthenticationFilter.class);
    }
}
*/
