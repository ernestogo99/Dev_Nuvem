package com.example.demo.shared.dto.response;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.demo.domain.model.Candy;

public record CrudLogResponseDTO(
        UUID id,
        String actionType,
        List<Map<String, String>> candies,
        String timestamp
) {
}
