package com.web.app.entity;

import lombok.*;

import javax.persistence.*;
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
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    //TODO: Про кеши 1го, 2го и 3го уровня
    //TODO: (для меня) посмотреть доку на @ManyToMany, @JoinTable и @JoinColumn
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_roles",
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "id")})
    private Set<RolesEntity> roles;

    @OneToMany(mappedBy = "usersid", cascade = CascadeType.ALL)
    private Set<AgendaEntity> agendas;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UsersEntity) {
            UsersEntity usersEntity = (UsersEntity) obj;
            return (usersEntity.getEmail().equals(((UsersEntity) obj).getEmail()) &&
                    usersEntity.getName().equals(((UsersEntity) obj).getName()));
        }
        return false;
    }
}
