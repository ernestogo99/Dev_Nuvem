package com.example.demo.shared.dto.response;

import com.example.demo.domain.enums.CandyType;

import java.math.BigDecimal;

public record CandyResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        String description,
        CandyType type,
        String imageUrl
) {
}
