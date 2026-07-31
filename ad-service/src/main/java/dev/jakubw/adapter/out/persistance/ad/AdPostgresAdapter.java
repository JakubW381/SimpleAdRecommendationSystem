package dev.jakubw.adapter.out.persistance.ad;

import dev.jakubw.adapter.out.persistance.provider.AdProviderEntity;
import dev.jakubw.adapter.out.persistance.provider.AdProviderPostgresRepository;
import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
public class AdPostgresAdapter implements AdRepositoryPort {

    private final AdPostgresRepository adPostgresRepository;
    private final AdProviderPostgresRepository adProviderPostgresRepository;

    @Override
    @Transactional
    public Ad createAd(Ad ad, String providerId) {
        AdProviderEntity providerEntity = adProviderPostgresRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider with id: " + providerId + " doesn't exist."));

        AdEntity entity = AdEntity.builder()
                .id(ad.getId())
                .name(ad.getName())
                .adUrl(ad.getAdUrl())
                .campaignEnd(ad.getCampaignEnd())
                .maxDayCount(ad.getMaxDayCount())
                .status(ad.getStatus())
                .provider(providerEntity)
                .build();

        AdEntity savedEntity = adPostgresRepository.save(entity);

        return new Ad(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getAdUrl(),
                savedEntity.getCampaignEnd(),
                savedEntity.getMaxDayCount(),
                savedEntity.getStatus()
        );
    }

    @Override
    public List<Ad> getAds(String providerId) {
        return adPostgresRepository.findByProviderId(providerId).stream().map(entity -> new Ad(
                entity.getId(),
                entity.getName(),
                entity.getAdUrl(),
                entity.getCampaignEnd(),
                entity.getMaxDayCount(),
                entity.getStatus()
        )).toList();
    }
}