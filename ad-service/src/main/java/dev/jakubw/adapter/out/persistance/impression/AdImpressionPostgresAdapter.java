package dev.jakubw.adapter.out.persistance.impression;

import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdImpressionPostgresAdapter implements AdImpressionRepositoryPort {

    private final AdImpressionPostgresRepository repository;
}
