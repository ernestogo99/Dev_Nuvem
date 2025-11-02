package com.example.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
record FieldError(
        String field,
        String message) {
}
