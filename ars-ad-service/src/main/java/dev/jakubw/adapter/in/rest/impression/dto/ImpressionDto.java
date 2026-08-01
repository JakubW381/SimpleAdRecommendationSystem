package dev.jakubw.adapter.in.rest.impression.dto;

import java.time.LocalDate;

public record ImpressionDto(
        String id,
        LocalDate day,
        Long count
) {
}
