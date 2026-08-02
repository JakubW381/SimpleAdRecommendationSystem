package dev.jakubw.adapter.in.rest.ad.dto;

import dev.jakubw.domain.model.AdTag;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateAdRequest (
        @NotEmpty(message = "Ad name can't be empty")
        String name,
        @NotEmpty(message = "Ad url can't be empty")
        String adUrl,
        @NotNull(message = "Campaign end date is required")
        LocalDate campaignEnd,
        @DecimalMin(value = "1", message = "Minimal daily ad count have to be at least 1")
        Long maxDayCount,
        @NotEmpty(message = "Ad tags are required.")
        Set<AdTag> tags
){ }
