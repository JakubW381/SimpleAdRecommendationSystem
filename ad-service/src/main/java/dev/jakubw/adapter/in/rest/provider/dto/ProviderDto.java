package dev.jakubw.adapter.in.rest.provider.dto;

import dev.jakubw.adapter.in.rest.ad.dto.AdDto;

import java.time.LocalDateTime;
import java.util.List;

public record ProviderDto(
        String id,
        String name,
        List<AdDto> ads,

        LocalDateTime creationDate
) {
}
