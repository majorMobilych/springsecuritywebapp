package com.web.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

//TODO: некоторые ентити наследуются от этого класса - для них аннотации будут работать?(Это про то, что я не хочу, чтоб
// все поля этого класса принимали участие а формировании иквеласа/хешкода для объектов классов - наследников)
@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created", updatable = false, nullable = false)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private Date created;

    @LastModifiedDate
    @Column(name = "updated", nullable = false)
    // TODO: правильно ли я тут поставил аннтацию?
    @EqualsAndHashCode.Exclude
    private Date updated;
}
