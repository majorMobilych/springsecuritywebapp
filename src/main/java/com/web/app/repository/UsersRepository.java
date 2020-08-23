package com.web.app.repository;

import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    UsersEntity findByEmailOrName(String email, String name);

    UsersEntity findByName(String name);

    UsersEntity findByEmail(String email);
}