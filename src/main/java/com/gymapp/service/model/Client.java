package com.gymapp.service.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Client {

    private Long id;
    private String email;
    private String name;
    private String password;

    private Set<ClientRole> roles;
}
