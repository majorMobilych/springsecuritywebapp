package com.web.app.security.jwt.providers;

import com.web.app.entity.RolesEntity;
import com.web.app.security.jwt.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
@PropertySource("properties/security/jwt.properties")
public class JwtProviderImpl {

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtProviderImpl(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private Long validTillMillis;

    @Value("${jwt.length}")
    private int tokenLength;

    @PostConstruct
    protected void init() {
        secret = Base64
                .getEncoder()
                .encodeToString(secret.getBytes());
    }

    public String provideToken(String username, Set<RolesEntity> roles) {
        Claims claims = (Claims) Jwts
                .claims()
                .setSubject(username)
                .put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validTill = new Date(now.getTime() + validTillMillis);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validTill)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        //TODO: откуда 'Bearer_' и что он значит?
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(tokenLength);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);

            return !claims
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private Set<String> getRoleNames(Set<RolesEntity> usersRoles) {
        return usersRoles
                .stream()
                .map(RolesEntity::getRole)
                .collect(Collectors.toSet());
    }

    public Authentication provideAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsernameByToken(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsernameByToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
