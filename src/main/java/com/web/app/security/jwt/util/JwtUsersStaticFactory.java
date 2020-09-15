package com.web.app.security.jwt.util;

import com.web.app.entity.UsersEntity;
import com.web.app.security.jwt.JwtUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUsersStaticFactory {

    private JwtUsersStaticFactory() {
        throw new AssertionError("UsersStaticFactory.class can't be instantiated");
    }

    public static JwtUserDetails EntityToJwt(UsersEntity usersEntity) {
        return new JwtUserDetails(
                usersEntity.getId(),
                usersEntity.getCreated(),
                usersEntity.getUpdated(),
                usersEntity.isValidity(),
                usersEntity.getEmail(),
                usersEntity.getName(),
                usersEntity.getPassword(),
                mapToGrantedAuthorities(usersEntity)
        );
    }

    private static Set<GrantedAuthority> mapToGrantedAuthorities(UsersEntity usersEntity) {
        return usersEntity.getRoles()
                .stream()
                .map(rolesEntity -> new SimpleGrantedAuthority(rolesEntity.getRole()))
                .collect(Collectors.toSet());
    }
}
