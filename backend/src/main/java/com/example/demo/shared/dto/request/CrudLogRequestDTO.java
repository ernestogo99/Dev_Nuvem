package com.example.demo.shared.dto.request;


import java.util.List;
import java.util.Map;

import com.example.demo.domain.model.Candy;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record CrudLogRequestDTO(
        @NotNull String actionType,
        List<Map<String, String>> candies,
        @NotNull String timestamp
        ) {
}
