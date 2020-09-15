package com.web.app.service;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;

import java.util.Collection;

public interface CommonUsersService {

    boolean save(UsersEntity usersEntity);

    UsersEntity findByNameAndPassword(AuthenticationRequestDTO authenticationRequestDTO);

    UsersEntity findByName(String name);

    Collection<AgendaEntity> findByUsersid(UsersEntity usersEntity);
}
