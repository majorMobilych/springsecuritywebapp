package com.web.app.security.jwt;

import com.web.app.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

public class JwtUser implements UserDetails {

    @Getter
    private final Date created;

    @Getter
    private final Date updated;

    private final Status status;

    @Getter
    private final String email;

    private final String name;

    private final String password;

    private final Set<? extends GrantedAuthority> authorities;

    public JwtUser(Date created,
                   Date updated,
                   Status status,
                   String email,
                   String name,
                   String password,
                   Set<? extends GrantedAuthority> authorities) {
        this.created = created;
        this.updated = updated;
        this.status = status;
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

    @Override
    public boolean isEnabled() {
        return status.isValid();
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
