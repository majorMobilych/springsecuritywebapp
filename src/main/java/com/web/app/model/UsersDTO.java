package com.web.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String email;
    private Set<AgendaEntity> agendaEntities;

    public static UsersDTO EntityToDTO(UsersEntity usersEntity) {
        return new UsersDTO(usersEntity.getId(), usersEntity.getEmail(), usersEntity.getAgendas());
    }
}
