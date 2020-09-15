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
    private final PasswordEncoder passwordEncoder;
    private final AgendaRepository agendaRepository;

    @Autowired
    public CommonUsersServiceImpl(UsersRepository usersRepository,
                                  RolesRepository rolesRepository,
                                  PasswordEncoder passwordEncoder,
                                  AgendaRepository agendaRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.agendaRepository = agendaRepository;
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

            log.info("IN registerUser - user {} successfully registered", usersEntity.getName());

            return true;
        }

        log.error("IN save, UsersServiceImpl.class - user, having email '" + usersEntity.getEmail() + "', or name: '" +
                usersEntity.getName() + "' already exists");

        return false;
    }

    private boolean isUserValidForSaving(UsersEntity usersEntity) {
        return usersRepository.findByEmailOrName(usersEntity.getEmail(), usersEntity.getName()) == null;
    }

    @Override
    public UsersEntity findByNameAndPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        UsersEntity usersEntityByName = usersRepository.findByName(authenticationRequestDTO.getName());

        if (passwordEncoder.matches(authenticationRequestDTO.getPassword(), usersEntityByName.getPassword())) {
            return usersEntityByName;
        }
        return null;
    }

    @Override
    public UsersEntity findByName(String name) {
        return usersRepository.findByName(name);
    }

    //ADDED
    @Override
    public Collection<AgendaEntity> findByUsersid(UsersEntity usersEntity) {
        Collection<AgendaEntity> usersAgendaByUsersid = agendaRepository.findByUsersid(usersEntity);
        // TODO: вот тут проверить. Где-то писали, что никогда не надо возвращать пустые коллекции.
        return Objects.requireNonNullElseGet(usersAgendaByUsersid, ArrayList::new);
    }
}
