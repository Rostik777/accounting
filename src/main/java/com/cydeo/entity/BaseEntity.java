package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false)
    LocalDateTime insertDateTime;

    @Column(nullable = false, updatable = false)
    public Long insertUserId;

    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;

    @Column(nullable = false)
    public Long lastUpdateUserId;

    private Boolean isDeleted = false;
}
