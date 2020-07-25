package com.web.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
//TODO: мне не нравится реализация иквелса: ломбок генерит код, проверяющий на равенство имена ролей И СЕТ ЮЗЕРСЕНТИТИ.
// (чекал по деломбоку), мно достаточно сделать this.getRole().equals(other.getRole()). А теперь представляем (изврат):
// Я добавляю роль, которая по имени совпадает с другой, но кидаю в нее других пользователей - роли как бы одинаковые,
// но equals() выдаст false -> выход - аннотация @EqualsAndHashCode.Exclude
@EqualsAndHashCode(callSuper = false)
public class RolesEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "role", unique = true, nullable = false)
    private String role;

    //TODO: а что, если будет роль, которая не соответсутвует ни одному юзеру (например, все модеры оказались)
    // не оч и я всех 'уволил', но модеры нужны), т.е. пока роль "модер" не соответсвует ни одному пользователю ->
    // как это отобразить?
    //TODO: ...вот тут прочекать
    //TODO: про доп. параметы в @ManyToMany(targetEntity, cascade, ...)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private Set<UsersEntity> users;
}
