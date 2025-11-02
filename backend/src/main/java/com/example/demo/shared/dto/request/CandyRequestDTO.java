package com.example.demo.shared.dto.request;

import com.example.demo.domain.enums.CandyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CandyRequestDTO(
        @NotBlank String name,
        @NotBlank String price,
        @NotBlank String description,
        @NotNull CandyType type
        ) {
}
