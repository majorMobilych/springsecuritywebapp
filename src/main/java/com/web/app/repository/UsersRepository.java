package com.web.app.repository;

import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository class, working with table 'users' from my database, using Spring Data JPA.
 */
@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    /*
     *  EXPLANATION: returns a UsersEntity, having a specified name.
     */
    UsersEntity findByName(String name);
}