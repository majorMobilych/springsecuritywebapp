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

    /*
     *  EXPLANATION: this method is used in validation for saving user in database. If there is no user in database,
     *              having specified name and email, the we can save this user. Else, we cant
     */
    Optional<UsersEntity> findByEmailAndName(String email, String name);
}