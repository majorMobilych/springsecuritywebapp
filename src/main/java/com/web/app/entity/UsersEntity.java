package com.web.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
public class UsersEntity extends BaseEntity {

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Id
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    //TODO: что такое фетч?
    //TODO: (для  меня) посмотреть доку на @ManyToMany, @JoinTable и @JoinColumn
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_roles",
            joinColumns = {@JoinColumn(name = "username", referencedColumnName = "name")},
            inverseJoinColumns = {@JoinColumn(name = "rolename", referencedColumnName = "role")})
    private Set<RolesEntity> roles;

    //TODO: прочекать правильность с AgendaEntity.class...
    @OneToMany(mappedBy = "username")
    private Set<AgendaEntity> agendas;
}
