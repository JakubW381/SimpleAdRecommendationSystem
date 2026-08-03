package dev.jakubw.adapter.in.dto;

import java.time.Instant;

public record ErrorMessageDto(
        Instant timestamp,
        Integer statusCode,
        String error,
        String message
) {
}
