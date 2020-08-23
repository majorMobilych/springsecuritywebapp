package com.web.app.security.jwt.util;

import com.web.app.entity.UsersEntity;
import com.web.app.security.jwt.JwtUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public final class UsersStaticFactory {

    private UsersStaticFactory() {
        throw new AssertionError("UsersStaticFactory.class can't be instantiated");
    }

    public static JwtUser EntityToJwt(UsersEntity usersEntity) {
        return new JwtUser(
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
        return usersEntity
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet());
    }
}
