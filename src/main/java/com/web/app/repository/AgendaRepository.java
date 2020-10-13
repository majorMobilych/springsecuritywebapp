package com.web.app.repository;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * A repository class, working with table 'agenda' from my database, using Spring Data JPA.
 */
@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    /*
     *  EXPLANATION: deletes agenda by it's id.
     */
    void deleteAgendaEntityById(Long id);

    /*
     *  EXPLANATION: returns all specified user's agenda.
     */
    Set<AgendaEntity> findByUsersid(UsersEntity usersEntity);
}