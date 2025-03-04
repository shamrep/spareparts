package com.spareparts.store.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Role {

    private Long id;
    private RoleEnum name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;
//    private long createdByClientWithId;
//    private Set<Permission> permissions;
}
