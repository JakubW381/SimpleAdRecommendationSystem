package dev.jakubw.domain.port.in.ad.model;

import java.time.LocalDate;

public record CreateAdCmdDto(
        String providerId,
        String name,
        String adUrl,
        LocalDate campaignEnd,
        Long maxDayCount
) {
}
