package com.example.demo.shared.dto.response;

import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.Candy;

import java.time.Instant;

public record CrudLogResponseDTO(
        Long id,
        LogActions action,
        Long candyId,
        Instant timeStamp
) {
}
