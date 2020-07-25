package com.web.app.repository.springdata;

import com.web.app.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, String> {

    UsersEntity findByEmail(String email);

    UsersEntity findByName(String name);

    void saveAndFlush(UsersEntity usersEntity);
}