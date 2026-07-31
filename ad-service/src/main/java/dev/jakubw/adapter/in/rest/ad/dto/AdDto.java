package dev.jakubw.adapter.in.rest.ad.dto;

import dev.jakubw.domain.model.AdStatus;

import java.time.LocalDate;

public record AdDto (
        String id,
        String name,
        String adUrl,
        LocalDate campaignEnd,
        Long maxDayCount,
        AdStatus status
){
}
