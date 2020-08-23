package com.web.app.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created", updatable = false, nullable = false)
    private Date created;

    @LastModifiedDate
    @Column(name = "updated", nullable = false)
    private Date updated;
}
