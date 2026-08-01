package dev.jakubw.adapter.in.rest.ad.dto;

import dev.jakubw.domain.model.AdStatus;
import dev.jakubw.domain.model.AdTags;

import java.time.LocalDate;
import java.util.Set;

public record AdDto (
        String id,
        String name,
        String adUrl,
        LocalDate campaignEnd,
        Long maxDayCount,
        AdStatus status,
        Set<AdTags> tags
){
}
