package com.example.demo.shared.dto.request;

import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.Candy;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CrudLogRequestDTO(
        @NotNull LogActions action,
        @Min(0) Long candyId,
        @NotNull Instant timeStamp
        ) {
}
