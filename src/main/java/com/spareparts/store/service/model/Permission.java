package com.spareparts.store.service.model;

import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
public class Permission {

    private Long id;
    private String name;
    private OffsetDateTime creationDate;

    public Permission() {

    }
}
