package com.web.app.rest;

import com.web.app.entity.UsersEntity;
import com.web.app.model.UsersDTO;
import com.web.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: \ДЛЯ ВСЕХ КОНТРОЛЛЕРОВ\ - ЕЩЕ ИХ НЕ РАЗБИРАЛ
@RestController
@RequestMapping(name = "/api/v1/user/")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{name}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable(name = "name") String name) {
        UsersEntity usersEntity = userService.findByUsername(name);

        if (usersEntity == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(UsersDTO.EntityToDTO(usersEntity), HttpStatus.OK);
    }
}
