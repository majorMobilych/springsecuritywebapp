package com.web.app.model;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * A DTO class, representing a login request, i.e. name and password.
 */
/*
 *  NOTE: Need no-args constructor for any DTO class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDTO {

    private String name;
    private String email;
    private Set<AgendaEntity> agendaEntities;

    public static UsersDTO EntityToDTO(UsersEntity usersEntity) {
        return new UsersDTO(
                usersEntity.getName(),
                usersEntity.getEmail(),
                usersEntity.getAgendas()
        );
    }
}
