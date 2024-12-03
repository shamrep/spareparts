package com.spareparts.store.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PermissionEntity {

    private Long id;
    private String name;
    private OffsetDateTime creationDate;
}
