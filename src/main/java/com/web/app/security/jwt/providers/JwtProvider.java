package com.web.app.security.jwt.providers;

import com.web.app.entity.RolesEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface JwtProvider {

    String provideToken(String username, Set<RolesEntity> roles);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);

    Authentication provideAuthentication(String token);
}
