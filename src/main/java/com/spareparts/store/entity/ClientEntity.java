package com.spareparts.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientEntity {
    private long id;
    private String email;
    private String name;
}
