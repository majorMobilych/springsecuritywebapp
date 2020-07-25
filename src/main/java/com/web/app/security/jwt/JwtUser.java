package com.web.app.security.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

public class JwtUser implements UserDetails {

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

    public JwtUser(Integer id,
                   Date created,
                   Date updated,
                   boolean validity,
                   String email,
                   String name,
                   String password,
                   Set<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.validity = validity;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

    //TODO: переделать
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
