package com.web.app.security;

import com.web.app.entity.UsersEntity;
import com.web.app.security.jwt.JwtUser;
import com.web.app.service.DefaultUsersService;
import com.web.app.security.jwt.util.UsersStaticFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final DefaultUsersService defaultUsersService;

    @Autowired
    public JwtUserDetailsService(DefaultUsersService defaultUsersService) {
        this.defaultUsersService = defaultUsersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = defaultUsersService.findByName(username);
        JwtUser jwtUser = UsersStaticFactory.EntityToJwt(user);

        log.info("IN loadUserByUsername - user, having userName: {} successfully loaded", username);

        return jwtUser;
    }
}
