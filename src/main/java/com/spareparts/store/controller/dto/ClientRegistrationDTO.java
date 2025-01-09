package com.spareparts.store.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientRegistrationDTO {
    private String email;
    private String password;
    private String name;
}
