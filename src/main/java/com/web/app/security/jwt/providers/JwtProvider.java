package com.web.app.security.jwt.providers;

import com.web.app.entity.RolesEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface JwtProvider extends UserDetailsService {

    String createToken(String username, Set<RolesEntity> roles);

    Authentication createAuthentication(String token);

    String getUsernameByToken(String token);

    boolean validateToken(String token);

    String resolveToken(HttpServletRequest req);
}
