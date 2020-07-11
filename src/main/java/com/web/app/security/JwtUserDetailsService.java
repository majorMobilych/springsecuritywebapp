package com.web.app.security;

import com.web.app.entity.UsersEntity;
import com.web.app.security.jwt.JwtUser;
import com.web.app.service.UserService;
import com.web.app.security.jwt.util.UsersStaticFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userService.findByUsername(username);
        JwtUser jwtUser = UsersStaticFactory.EntityToJwt(user);

        log.info("IN loadUserByUsername - user, having userName: {} successfully loaded", username);

        return jwtUser;
    }
}
