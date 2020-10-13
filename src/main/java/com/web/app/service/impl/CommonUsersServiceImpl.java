package com.web.app.service.impl;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.request.SignInRequestDTO;
import com.web.app.model.request.SignUpRequestDTO;
import com.web.app.repository.AgendaRepository;
import com.web.app.repository.RolesRepository;
import com.web.app.repository.UsersRepository;
import com.web.app.service.CommonUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@PropertySource("properties/service/service.properties")
@Slf4j
public class CommonUsersServiceImpl implements CommonUsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final AgendaRepository agendaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CommonUsersServiceImpl(UsersRepository usersRepository,
                                  RolesRepository rolesRepository,
                                  AgendaRepository agendaRepository,
                                  PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.agendaRepository = agendaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${user.role.default}")
    private String defaultRole;

    private Set<RolesEntity> getDefaultUserRole() {
        RolesEntity rolesEntity = rolesRepository.findByRole(defaultRole);

        Set<RolesEntity> defaultUserRoles = new HashSet<>();
        defaultUserRoles.add(rolesEntity);

        return defaultUserRoles;
    }

    @Override
    public boolean save(SignUpRequestDTO signUpRequestDTO) {
        Set<RolesEntity> defaultRolesEntity = getDefaultUserRole();

        if (isUserValidForSaving(signUpRequestDTO)) {
            UsersEntity userToSave = new UsersEntity();
            userToSave.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
            userToSave.setRoles(defaultRolesEntity);
            userToSave.setValidity(true);

            usersRepository.saveAndFlush(userToSave);

            log.info("IN CommonUsersServiceImpl.class, save(UsersEntity usersEntity) - user {} successfully registered",
                    userToSave.getName());

            return true;
        }

        log.info("IN CommonUsersServiceImpl.class, save(UsersEntity usersEntity) - user, having email '{}', " +
                "or name: '{}' already exists", signUpRequestDTO.getEmail(), signUpRequestDTO.getName());

        return false;
    }

    private boolean isUserValidForSaving(SignUpRequestDTO signUpRequestDTO) {
        return usersRepository.findByEmailAndName(signUpRequestDTO.getEmail(), signUpRequestDTO.getName()).isEmpty();
    }

    @Override
    public UsersEntity findByName(String name) {
        return usersRepository.findByName(name);
    }

    @Override
    public Set<AgendaEntity> findByUsersid(UsersEntity usersEntity) {
        Set<AgendaEntity> usersAgendaByUsersid = agendaRepository.findByUsersid(usersEntity);

       if (usersAgendaByUsersid == null) {
           return new HashSet<>();
       }

       return usersAgendaByUsersid;
    }
}
