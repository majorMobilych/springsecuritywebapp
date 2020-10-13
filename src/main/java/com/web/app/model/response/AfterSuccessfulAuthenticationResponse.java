package com.web.app.model.response;

import com.web.app.entity.AgendaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AfterSuccessfulAuthenticationResponse {

    public String name;

    public Set<AgendaEntity> agendaEntities;
}
