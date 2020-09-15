package com.web.app.security;

import com.web.app.entity.UsersEntity;
import com.web.app.security.jwt.JwtUserDetails;
import com.web.app.service.CommonUsersService;
import com.web.app.security.jwt.util.JwtUsersStaticFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final CommonUsersService commonUsersService;

    @Autowired
    public JwtUserDetailsService(CommonUsersService commonUsersService) {
        this.commonUsersService = commonUsersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = commonUsersService.findByName(username);

        if (usersEntity == null) {
            throw new UsernameNotFoundException("IN loadUserByUsername - user, having userName'" + username + "' " +
                    "doesn't exists");
        }
        JwtUserDetails jwtUserDetails = JwtUsersStaticFactory.EntityToJwt(usersEntity);

        log.info("IN loadUserByUsername - user, having userName: {} successfully loaded", username);
        return jwtUserDetails;
    }
}
