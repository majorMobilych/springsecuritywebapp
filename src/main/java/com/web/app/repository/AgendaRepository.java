package com.web.app.repository;

import com.web.app.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    void deleteAgendaEntityById(Long id);
}