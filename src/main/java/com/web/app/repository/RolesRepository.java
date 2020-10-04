package com.web.app.repository;

import com.web.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository class, working with table 'roles' from my database, using Spring Data JPA.
 */
@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, String> {

    /*
     *  EXPLANATION: returns a RoleEntity, having a specified name.
     */
    RolesEntity findByRole(String role);

    /*
     *  EXPLANATION: returns all data from the 'roles' table.
     */
    List<RolesEntity> findAll();
}
