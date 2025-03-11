package com.gymapp.repository.entity;

import com.gymapp.service.model.ClientRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ClientEntity {

    private Long id;
    private String email;
    private String name;
    private String password;
    private Set<ClientRole> roles;

}
