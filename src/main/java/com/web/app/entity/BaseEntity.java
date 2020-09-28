package com.web.app.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * A superclass for some entities, i.e. some entities has fields 'created' and 'updated', meaning,
 * we can declare this as an abstract class and all entities, having this fields, should extend this class.
 */
/*
 *  EXPLANATION: @MappedSuperclass means that there is no table in database, having ONLY this fields, meaning, it
 *              designates a class whose mapping information is applied to the entities that inherit from it.
 *              A mapped superclass has no separate table defined for it.
 */
@MappedSuperclass
/*
 *  EXPLANATION: @NoArgsConstructor generates an empty constructor. We need it for serialization magic.
 */
@NoArgsConstructor
/*
 *  EXPLANATION: @Getter generates getters for all class fields.
 */
@Getter
/*
 *  EXPLANATION: @Setter generates setters for all class fields.
 */
@Setter
abstract class BaseEntity {

    /*
     *  EXPLANATION: @CreatedDate declares a field as the one representing the date the entity containing the field was
     *              created.
     */
    @CreatedDate
    /*
     *  EXPLANATION: @Column(...) specifies field's properties. See my Liquibase scripts for details.
     */
    @Column(name = "created", updatable = false, nullable = false)
    private Date created;

    /*
     *  EXPLANATION: @LastModifiedDate declares a field as the one representing the date the entity containing the field
     *              was recently modified.
     */
    @LastModifiedDate
    /*
     *  EXPLANATION: @Column(...) specifies field's properties. See my Liquibase scripts for details.
     */
    @Column(name = "updated", nullable = false)
    private Date updated;
}
