package com.example.demo.shared.dto.response;

import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.Candy;

import java.time.Instant;
import java.util.UUID;

public record CrudLogResponseDTO(
        UUID id,
        String actionType,
        Long candyId,
        String timestamp
) {
}
