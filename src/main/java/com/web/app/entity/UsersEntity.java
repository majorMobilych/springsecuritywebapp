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

    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private String email;

    @Basic
    @Column(name = "validity", nullable = false)
    @EqualsAndHashCode.Exclude
    private boolean validity;

    @Basic
    @Column(name = "name")
    @EqualsAndHashCode.Exclude
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private String password;

    //TODO: что такое фетч?
    //TODO: (для меня) посмотреть доку на @ManyToMany, @JoinTable и @JoinColumn
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_roles",
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "id")})
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private Set<RolesEntity> roles;

    //TODO: прочекать правильность с AgendaEntity.class...(НЕ РАБОТАЕТ)
    @OneToMany(mappedBy = "usersid", cascade = CascadeType.ALL, orphanRemoval = true)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private Set<AgendaEntity> agendas;
}
