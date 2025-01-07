package com.spareparts.store.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientCredentials {
    private String name;
    private String email;
    private String password;
}
