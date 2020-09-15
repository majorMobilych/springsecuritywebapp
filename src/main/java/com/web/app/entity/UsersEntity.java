package com.web.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "validity", nullable = false)
    private boolean validity;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    /*
     *  NOTE: check parameters if database was altered
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_roles",
            joinColumns = {@JoinColumn(name = "usersid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rolesid", referencedColumnName = "id")})
    private Set<RolesEntity> roles;

    @OneToMany(mappedBy = "usersid", cascade = CascadeType.ALL)
    private Set<AgendaEntity> agendas;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UsersEntity) {
            UsersEntity usersEntity = (UsersEntity) obj;
            return Objects.equals(this.name, usersEntity.getName());
        }
        return false;
    }
}
