package com.web.app.security.jwt.filters;

import com.web.app.security.jwt.providers.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    //TODO: почему подсвечивается автоваерд?
    @Autowired
    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
            ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) req);

        if (token != null && jwtProvider.validateToken(token)) {
            //TODO: (для меня) почитать доку.
            Authentication authentication = jwtProvider.createAuthentication(token);

            if (authentication != null) {
                //TODO: (для меня) почитать доку.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(req, res);
    }

}
