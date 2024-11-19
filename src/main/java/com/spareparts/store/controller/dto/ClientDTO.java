package com.spareparts.store.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
}
