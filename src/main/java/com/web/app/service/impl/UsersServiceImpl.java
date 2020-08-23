package com.web.app.service.impl;

import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
import com.web.app.repository.RolesRepository;
import com.web.app.repository.UsersRepository;
import com.web.app.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@PropertySource("properties/service/service.properties")
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("default_role")
    private String default_role;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Set<RolesEntity> getDefaultUserRole() {
        RolesEntity rolesEntity = rolesRepository.findByRole(default_role);
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

        log.error("IN save, UsersServiceImpl.class - user, having email: " + usersEntity.getEmail() + ", or name: " +
                usersEntity.getName() + " already exists");

        return false;
    }

    private boolean isUserValidForSaving(UsersEntity usersEntity) {
        return usersRepository.findByEmailOrName(usersEntity.getEmail(), usersEntity.getName()) == null;
    }

    @Override
    public UsersEntity findByEmailAndPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        UsersEntity usersRepositoryByEmail = usersRepository.findByEmail(authenticationRequestDTO.getEmail());

        if (passwordEncoder.matches(authenticationRequestDTO.getPassword(), usersRepositoryByEmail.getPassword())) {
            return usersRepositoryByEmail;
        }
        return null;
    }

    @Override
    public UsersEntity findByName(String name) {
        return usersRepository.findByName(name);
    }
}
