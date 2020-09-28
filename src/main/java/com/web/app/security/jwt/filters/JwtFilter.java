/*
package com.web.app.security.jwt.filters;

import com.web.app.security.jwt.providers.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
            ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) req);

        Authentication authentication = null;

        if (token != null && jwtProvider.validateToken(token)) {
            try {
                 authentication = jwtProvider.provideAuthentication(token);
            } catch (AuthenticationException e) {
                log.info("IN doFilter - couldn't create authentication");
            }

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(req, res);
    }
}
 */
