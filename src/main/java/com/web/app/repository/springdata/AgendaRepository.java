package com.web.app.repository.springdata;

import com.web.app.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    Set<AgendaEntity> findAllByUsername(String username);
}