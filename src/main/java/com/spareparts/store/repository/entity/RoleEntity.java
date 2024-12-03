package com.spareparts.store.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    private String name;
    private OffsetDateTime creationDate;
}
