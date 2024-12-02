package com.spareparts.store.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    private Long id;

//    @Column(nullable = false, unique = true)
    private String name;

//    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

//    @Column(name = "created_by")
    private String createdBy;

//    @ManyToMany
//    @JoinTable(
//            name = "role_permissions",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
}
