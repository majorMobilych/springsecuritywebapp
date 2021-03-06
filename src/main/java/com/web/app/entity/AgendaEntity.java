package com.web.app.entity;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;

/**
 * An entity, representing agenda.
 */
@Entity
/*
 *  EXPLANATION: @Table is usually used to specify table's name in database.
 */
@Table(name = "agenda")
/*
 *  EXPLANATION: @NoArgsConstructor generates an empty constructor. We need it for serialization magic.
 */
@NoArgsConstructor
/*
 *  EXPLANATION: @AllArgsConstructor Generates an all-args constructor. We'll need it further.
 */
@AllArgsConstructor
/*
 *  EXPLANATION: @Getter generates getters for all class fields.
 */
@Getter
/*
 *  EXPLANATION: @Setter generates setters for all class fields.
 */
@Setter
/*
 *  EXPLANATION: this class extends BaseEntity because it contains 'created' and 'updated' fields.
 */
public class AgendaEntity extends BaseEntity {

    /*
     *  EXPLANATION: @Id Specifies that tis field is a primary key of this entity.
     */
    @Id
    /*
     *  EXPLANATION: @GeneratedValue specifies, how primary keys will be assigned.
     *              strategy = GenerationType.IDENTITY means, that this operation will be executed by my database,
     *              as far as, strategy was specified in my Liquibase scrips.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
     *  EXPLANATION: @Column specifies name for this field in database.
     */
    @Column(name = "id")
    private Long id;

    /*
     *  EXPLANATION: @ManyToOne Specifies a single-valued association to another entity class that has
     *              many-to-one multiplicity.
     *                  In other words, one user may have lots of agendas, but any agenda corresponds to a single user,
     *              we refer to this particular user by his id and @JoinColumn indicates this.
     */
    @ManyToOne
    @JoinColumn(name = "usersid")
    private UsersEntity usersid;

    /*
     *  EXPLANATION: @Enumerated Specifies that a field should be persisted as an enumerated type,
     *              days of week are enumerable: MONDAY, TUESDAY, ... .
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private DayOfWeek day;

    /*
     *  EXPLANATION: @Basic specifies that a field in database has no special properties.
     */
    @Basic
    /*
     * ... . Also note, that property 'nullable' defines if this field can be null or not. Set it to 'false'.
     */
    @Column(name = "time", nullable = false)
    private String time;

    /*
     *  ...
     */
    @Basic
    @Column(name = "privacy", nullable = false)
    private boolean privacy;

    /*
     *  ...
     */
    @Basic
    @Column(name = "note", nullable = false)
    private String note;
}
