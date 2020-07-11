package com.web.app.repository;

import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    UsersEntity findByEmail(String email);

    UsersEntity findByName(String name);
}