package com.web.app.repository;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    void deleteAgendaEntityById(Long id);

    //TODO: не выкупаю, почему тут параметр UsersEntity
    Collection<AgendaEntity> findByUsersid(UsersEntity usersEntity);
}