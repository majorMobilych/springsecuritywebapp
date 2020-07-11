package com.web.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = false)
public class RolesEntity extends BaseEntity {

    @Id
    @Column(name = "role")
    private String role;

    //TODO: а что, если будет роль, которая не соответсутвует ни одному юзеру (например, все модеры оказались)
    // не оч и я всех 'уволил', но модеры нужны), т.е. пока роль "модер" не соответсвует ни одному пользователю ->
    // как это отобразить?
    //TODO: ...вот тут прочекать
    //TODO: про доп. параметы в @ManyToMany(targetEntity, cascade, ...)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<UsersEntity> users;
}
