package com.spareparts.store.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class Permission {

    private Long id;
    private String name;
//    private OffsetDateTime creationDate;

    public Permission() {

    }
}
