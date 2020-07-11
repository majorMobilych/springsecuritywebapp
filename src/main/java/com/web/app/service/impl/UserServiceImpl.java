package com.web.app.service.impl;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.RolesEntity;
import com.web.app.entity.Status;
import com.web.app.entity.UsersEntity;
import com.web.app.repository.AgendaRepository;
import com.web.app.repository.RolesRepository;
import com.web.app.repository.UsersRepository;
import com.web.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@PropertySource("properties/service.properties")
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final AgendaRepository agendaRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO: role.default - проперти, которое ость в файле. После двоеточия идет дефолтное значение, если проперти
    // отсутствует в файле. Возможно, не будет работать. Проверить.
    @Value("${role.default:user}")
    private String defaultRole;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository,
                           RolesRepository rolesRepository,
                           AgendaRepository agendaRepository,
                           PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.agendaRepository = agendaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UsersEntity usersEntity) {
        RolesEntity rolesEntity = rolesRepository.findByRole(defaultRole);
        Set<RolesEntity> userRoles = new HashSet<>();
        userRoles.add(rolesEntity);

        if (isUserValidForSaving(usersEntity)) {
            usersEntity.setPassword(passwordEncoder.encode(usersEntity.getPassword()));
            usersEntity.setRoles(userRoles);
            usersEntity.setStatus(Status.valid);

            //TODO: есть метод save() в CrudRepository, а есть saveAndFlush() в JpaRepository. В чем разница?
            // + подробнее про флаш
            usersRepository.saveAndFlush(usersEntity);

            log.info("IN registerUser - user {} successfully registered", usersEntity.getName());
        }
        //TODO: кинуть сюда юзерОлредиЭкзистсЭксепшн.
    }

    private boolean isUserValidForSaving(UsersEntity usersEntity) {
         return usersRepository.findByEmail(usersEntity.getEmail()) == null
                && usersRepository.findByName(usersEntity.getName()) == null;
    }

    @Override
    public UsersEntity findByUsername(String username) {
        UsersEntity user = usersRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User, having username = :" + username + " not found");
        }

        log.info("IN findByUserName - user {} was found", username);

        return user;
    }

    @Override
    public Set<AgendaEntity> getUsersAgendaByUsername(String username) {
        Set<AgendaEntity> usersAgenda = agendaRepository.findAllByUsername(username);

        if (usersAgenda == null || usersAgenda.isEmpty()) {
            //TODO: надо логику сюда написать.
        }

        log.info("In getUsersAgendaByUsername - " + username + "'s agenda was found");

        return usersAgenda;
    }
}
