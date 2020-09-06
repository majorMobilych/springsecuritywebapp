package com.web.app.service;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;

public interface DefaultUsersService {

    boolean save(UsersEntity usersEntity);

    UsersEntity findByEmailAndPassword(AuthenticationRequestDTO authenticationRequestDTO);

    UsersEntity findByName(String name);
}