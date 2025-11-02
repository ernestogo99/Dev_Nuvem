package com.example.demo.shared.dto.response;

import com.example.demo.domain.enums.CandyType;

public record CandyResponseDTO(
        Long id,
        String name,
        String price,
        String description,
        CandyType type
) {
}
