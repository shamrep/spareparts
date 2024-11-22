package com.spareparts.store.repository.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ClientEntity {

    private long id;
    private String email;
    private String name;
    private String password;
}
