package com.web.app.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

    @Getter
    private final Integer id;

    @Getter
    private final Date created;

    @Getter
    private final Date updated;

    @Getter
    private final boolean validity;

    @Getter
    private final String email;

    private final String name;

    private final String password;

    private final Set<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return validity;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
