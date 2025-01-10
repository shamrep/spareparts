package com.spareparts.store.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientCredentialsDTO {

    private String email;
    private String password;

}
