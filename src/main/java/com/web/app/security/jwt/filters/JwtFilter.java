package com.web.app.security.jwt.filters;

import com.web.app.security.jwt.providers.JwtProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtProviderImpl JwtProviderImpl;

    @Autowired
    public JwtFilter(JwtProviderImpl JwtProviderImpl) {
        this.JwtProviderImpl = JwtProviderImpl;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
            ServletException {
        String token = JwtProviderImpl.resolveToken((HttpServletRequest) req);

        if (token != null && JwtProviderImpl.validateToken(token)) {
            Authentication authentication = JwtProviderImpl.provideAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(req, res);
    }
}
