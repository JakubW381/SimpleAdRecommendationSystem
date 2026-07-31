package dev.jakubw.adapter.in.rest.provider.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateProviderRequest(
        @NotEmpty(message = "Provider name is required")
        String name
) { }
