package com.web.app.service.impl;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
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
    public boolean save(UsersEntity usersEntity) {
        Set<RolesEntity> defaultRolesEntity = getDefaultUserRole();

        if (isUserValidForSaving(usersEntity)) {
            usersEntity.setPassword(passwordEncoder.encode(usersEntity.getPassword()));
            usersEntity.setRoles(defaultRolesEntity);
            usersEntity.setValidity(true);

            usersRepository.saveAndFlush(usersEntity);

            log.info("IN CommonUsersServiceImpl.class, save(UsersEntity usersEntity) - user {} successfully registered",
                    usersEntity.getName());

            return true;
        }

        log.info("IN CommonUsersServiceImpl.class, save(UsersEntity usersEntity) - user, having email '{}', " +
                "or name: '{}' already exists", usersEntity.getEmail(), usersEntity.getName());

        return false;
    }

    private boolean isUserValidForSaving(UsersEntity usersEntity) {
        return usersRepository.findByName(usersEntity.getEmail()) == null;
    }

    @Override
    public UsersEntity findByNameAndPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        UsersEntity usersEntityByName = findByName(authenticationRequestDTO.getName());

        if (usersEntityByName != null) {
            if (passwordEncoder.matches(authenticationRequestDTO.getPassword(), usersEntityByName.getPassword())) {
                return usersEntityByName;
            }
        }

        return null;
    }

    @Override
    public UsersEntity findByName(String name) {
        return usersRepository.findByName(name);
    }

    @Override
    public Collection<AgendaEntity> findByUsersid(UsersEntity usersEntity) {
        Collection<AgendaEntity> usersAgendaByUsersid = agendaRepository.findByUsersid(usersEntity);

        return Objects.requireNonNullElseGet(usersAgendaByUsersid, ArrayList::new);
    }
}
