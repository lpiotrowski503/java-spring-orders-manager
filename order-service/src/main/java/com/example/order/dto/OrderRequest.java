package com.example.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Schema(example = "Apple Inc.")
    @NotBlank
    private String name;

    @Schema(example = "AAPL")
    @NotBlank
    private String symbol;

    @Schema(example = "100")
    @NotNull
    private Integer quantity;

    @Schema(example = "BUY")
    @NotBlank
    private String side;

    @Schema(example = "NASDAQ")
    @NotBlank
    private String exchange;

    @Schema(example = "179.45")
    @NotNull
    private BigDecimal price;

    @Schema(example = "LIMIT")
    @NotBlank
    private String type;

    @Schema(example = "report_Q1.pdf")
    private String attachment;
}

