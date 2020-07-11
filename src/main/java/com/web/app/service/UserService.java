package com.web.app.service;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;

import java.util.Set;

public interface UserService {

    void registerUser(UsersEntity usersEntity);

    UsersEntity findByUsername(String username);

    Set<AgendaEntity> getUsersAgendaByUsername(String username);
}
