package com.spareparts.store.service.model;

import java.time.OffsetDateTime;
import java.util.Set;

public class Role {

    private Long id;
    private String name;
    private OffsetDateTime creationDate;
    private String createdBy;
    private Set<Permission> permissions;
}
