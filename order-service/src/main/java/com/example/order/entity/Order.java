package com.example.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String symbol;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String side;

    @NotBlank
    private String exchange;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String type;

    private String attachment; // ścieżka lub URL
}

