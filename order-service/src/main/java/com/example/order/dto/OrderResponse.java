package com.example.order.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private String name;
    private String symbol;
    private Integer quantity;
    private String side;
    private String exchange;
    private BigDecimal price;
    private String type;
    private String attachment;
}

