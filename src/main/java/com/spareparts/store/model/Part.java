package com.spareparts.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "parts")
//@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Part {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)z`
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
