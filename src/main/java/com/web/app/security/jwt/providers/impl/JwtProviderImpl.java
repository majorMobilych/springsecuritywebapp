package com.web.app.security.jwt.providers.impl;

import com.web.app.entity.RolesEntity;
import com.web.app.security.jwt.exceptions.JwtAuthenticationException;
import com.web.app.security.jwt.providers.JwtProvider;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@PropertySource("properties/jwt.properties")
public class JwtProviderImpl implements JwtProvider {
    private final UserDetailsService userDetailsService;

    private final int TOKEN_LENGTH = 7;

    @Autowired
    public JwtProviderImpl(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.token.secret}")
    private String secret;

    // TODO: проперти считается и автоматически закастится в лонг?
    @Value("${jwt.token.secret}")
    private Long validityMillis;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @PostConstruct
    protected void init() {
        secret = Base64
                .getEncoder()
                .encodeToString(secret.getBytes());
    }

    @Override
    public String createToken(String username, Set<RolesEntity> roles) {
        Claims claims = (Claims) Jwts
                .claims()
                .setSubject(username)
                .put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMillis);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Set<String> getRoleNames(Set<RolesEntity> usersRoles) {
        return usersRoles
                .stream()
                .map(RolesEntity::getRole)
                .collect(Collectors.toSet());
    }

    @Override
    public Authentication createAuthentication(String token) {
        UserDetails userDetails = this
                .userDetailsService
                .loadUserByUsername(getUsernameByToken(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
        //TODO: а "Authorization" всегда будет в начале точена стоять?
        String bearerToken = req.getHeader("Authorization");
        //TODO: что делать с бэрэром?
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(TOKEN_LENGTH);
        }
        return null;
    }

    @Override
    public String getUsernameByToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }
}
