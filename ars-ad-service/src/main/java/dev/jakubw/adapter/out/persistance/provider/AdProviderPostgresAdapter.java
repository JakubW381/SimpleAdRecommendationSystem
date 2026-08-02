package dev.jakubw.adapter.out.persistance.provider;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.model.AdProvider;
import dev.jakubw.domain.port.out.provider.AdProviderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class AdProviderPostgresAdapter implements AdProviderRepositoryPort {

    private final AdProviderPostgresRepository repository;

    @Override
    @Transactional
    public AdProvider registerProvider(AdProvider provider) {
        AdProviderEntity providerEntity = AdProviderEntity.builder()
                .id(provider.getId())
                .name(provider.getName())
                .creationDate(provider.getCreationDate())
                .build();

        AdProviderEntity saved = repository.save(providerEntity);

        return new AdProvider(
                saved.getId(),
                saved.getName(),
                saved.getAds().stream().map(entity -> new Ad(
                        entity.getId(),
                        entity.getName(),
                        entity.getAdUrl(),
                        entity.getCampaignEnd(),
                        entity.getMaxDayCount(),
                        entity.getStatus(),
                        entity.getTags()
                )).toList(),
                saved.getCreationDate()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AdProvider getProvider(String id) {
        AdProviderEntity providerEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No provider with id:" + id));

        AdProvider adProvider = new AdProvider(
                providerEntity.getId(),
                providerEntity.getName(),
                providerEntity.getAds().stream().map(adEntity ->
                        new Ad(
                                adEntity.getId(),
                                adEntity.getName(),
                                adEntity.getAdUrl(),
                                adEntity.getCampaignEnd(),
                                adEntity.getMaxDayCount(),
                                adEntity.getStatus(),
                                adEntity.getTags()
                        )).toList(),
                providerEntity.getCreationDate()
        );
        return adProvider;
    }
}
