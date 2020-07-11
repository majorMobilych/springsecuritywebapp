package com.web.app.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created", updatable = false, nullable = false)
    private Date created;

    @LastModifiedDate
    @Column(name = "updated", nullable = false)
    private Date updated;

    //TODO: статусов может быть много. Я хочу, чтоб сюда засовывались все
    // (как различные имплементации некоторого интерфейса). Как решить проблему?
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
