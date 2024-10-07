package com.spareparts.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Part {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
