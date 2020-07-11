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
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: возможно неправильно
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "name", nullable = false)
    //TODO: надо ли ставиь колум? Как сочитается с верхними аннотациями? Ее вроде как можно и не ставить, но тогда будет
    // (по дефолту) считаться, что в таблице колонка называется так же, как и наменование поля (String -> username <-).
    // Но я хочу указывать название колонки явно.
    @Column(name = "username")
    private String username;

    //TODO: это енам java.time.DayOfWeek. Нормально?
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayOfWeek day;

    @Basic
    @Column(name = "time", nullable = false, length = 5)
    private String time;

    @Basic
    @Column(name = "privacy")
    private boolean privacy;

    @Basic
    @Column(name = "note", nullable = false)
    private String note;
}
