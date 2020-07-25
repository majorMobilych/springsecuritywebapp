package com.web.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "agenda")
@Data
@EqualsAndHashCode(callSuper = false)
public class AgendaEntity extends BaseEntity {

    @Id
    //TODO: про генерейтедВэлью ничего не понял
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: возможно неправильно
    @ManyToOne
    @JoinColumn(name = "usersid")
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private UsersEntity usersid;

    //TODO: это енам java.time.DayOfWeek. Нормально?
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private DayOfWeek day;

    @Basic
    @Column(name = "time", nullable = false, length = 5)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private String time;

    @Basic
    @Column(name = "privacy", nullable = false)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private boolean privacy;

    @Basic
    @Column(name = "note", nullable = false)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private String note;
}
