package com.spareparts.store.service.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Client {
    private Long id;
    private String email;
    private String name;
    private String password;
}
