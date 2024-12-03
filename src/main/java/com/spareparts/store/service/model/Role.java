package com.spareparts.store.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Role {

    private Long id;
    private String name;
    private OffsetDateTime creationDate;
    private long createdByClientWithId;
    private Set<Permission> permissions;
}
