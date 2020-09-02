package com.web.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "agenda")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgendaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usersid")
    private UsersEntity usersid;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private DayOfWeek day;

    @Basic
    @Column(name = "time", nullable = false, length = 5)
    private String time;

    @Basic
    @Column(name = "privacy", nullable = false)
    private boolean privacy;

    @Basic
    @Column(name = "note", nullable = false)
    private String note;
}
