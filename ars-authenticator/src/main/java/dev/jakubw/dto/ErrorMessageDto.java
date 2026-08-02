package dev.jakubw.dto;

import java.time.Instant;

public record ErrorMessageDto(
        Instant timestamp,
        Integer statusCode,
        String error,
        String message
) {
}
