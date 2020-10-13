package com.web.app.service;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.request.SignUpRequestDTO;

import java.util.Set;

public interface CommonUsersService {

    boolean save(SignUpRequestDTO signUpRequestDTO);

    UsersEntity findByName(String name);

    Set<AgendaEntity> findByUsersid(UsersEntity usersEntity);
}
