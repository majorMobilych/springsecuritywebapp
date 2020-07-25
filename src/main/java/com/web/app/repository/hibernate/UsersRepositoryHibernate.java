package com.web.app.repository.hibernate;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;

public interface UsersRepositoryHibernate {

    void save(UsersEntity usersEntity);

    UsersEntity loginByEmail(AuthenticationRequestDTO loginPassword);

    void clear();
}
