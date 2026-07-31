package dev.jakubw.adapter.in.rest.ad.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateAdRequest (
        @NotEmpty(message = "Ad provider ID can't be empty")
        String providerId,
        @NotEmpty(message = "Ad name can't be empty")
        String name,
        @NotEmpty(message = "Ad url can't be empty")
        String adUrl,
        @NotNull(message = "Campaign end date is required")
        LocalDate campaignEnd,
        @DecimalMin(value = "1", message = "Minimal daily ad count have to be at least 1")
        Long maxDayCount
){ }
