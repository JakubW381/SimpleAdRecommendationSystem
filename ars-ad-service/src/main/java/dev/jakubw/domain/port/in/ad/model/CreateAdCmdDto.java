package dev.jakubw.domain.port.in.ad.model;

import dev.jakubw.domain.model.AdTag;

import java.time.LocalDate;
import java.util.Set;

public record CreateAdCmdDto(
        String providerId,
        String name,
        String adUrl,
        LocalDate campaignEnd,
        Long maxDayCount,
        Set<AdTag> tags
) {
}
