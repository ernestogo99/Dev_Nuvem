package com.example.demo.exceptions;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(
        description = "DTO that encapsulates detailed information about errors or exceptions."
)
@JsonPropertyOrder(alphabetic = true)
public record ExceptionDto(
        @Schema(description = "List of field errors detailing the specific issues with the request.")
        List<FieldError> errors,

        @Schema(description = "Timestamp of when the error occurred.", example = "2025-03-10T15:30:45.12345Z")
        Instant timestamp,

        @Schema(description = "Title of the exception, usually a brief description of the error.", example = "Bad Request")
        String title,

        @Schema(description = "HTTP status code associated with the error.", example = "400")
        Integer status,

        @Schema(description = "A detailed description of the error or exception that occurred.", example = "Missing or invalid required fields.")
        String detail,

        @Schema(description = "URI of the instance that caused the error.", example = "/api/v1/users/:id")
        String instance
) {
}