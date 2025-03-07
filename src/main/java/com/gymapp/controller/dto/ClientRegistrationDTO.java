package com.gymapp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ClientRegistrationDTO {

    private String email;
    private String password;
    private String name;

}
