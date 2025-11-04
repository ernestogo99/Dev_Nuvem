package com.example.demo.shared.dto.request;

import com.example.demo.domain.enums.CandyType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CandyRequestDTO(
        @NotBlank String name,
        @Min(0) BigDecimal price,
        @NotBlank String description,
        @NotNull CandyType type
        ) {
}
