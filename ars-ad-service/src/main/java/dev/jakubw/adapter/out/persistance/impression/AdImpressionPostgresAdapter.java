package dev.jakubw.adapter.out.persistance.impression;

import dev.jakubw.adapter.out.persistance.ad.AdEntity;
import dev.jakubw.adapter.out.persistance.ad.AdPostgresRepository;
import dev.jakubw.domain.model.AdDailyImpression;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AdImpressionPostgresAdapter implements AdImpressionRepositoryPort {

    private final AdImpressionPostgresRepository repository;
    private final AdPostgresRepository adPostgresRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AdDailyImpression> findByAdIdAndProviderId(String adId, String providerId) {
        List<AdImpressionEntity> entities = repository.findByAdIdAndAdProviderId(adId, providerId);
        return entities.stream().map(entity -> new AdDailyImpression(
                entity.getId(),
                entity.getDay(),
                entity.getCount()
        )).toList();
    }

    @Override
    @Transactional
    public void persistImpressions(Map<String, Long> impressions) {
        if (impressions.isEmpty()) {
            return;
        }
        LocalDate today = LocalDate.now();
        List<String> adIds = new ArrayList<>(impressions.keySet());

        List<AdImpressionEntity> existingImpressions =
                repository.findByAdIdsAndDay(adIds, today);

        Map<String, AdImpressionEntity> existingMap =
                existingImpressions.stream()
                        .collect(Collectors.toMap(
                                entity -> entity.getAd().getId(),
                                Function.identity()
                        ));

        List<String> missingImpressions  = adIds.stream()
                .filter(id -> !existingMap.containsKey(id))
                .toList();

        Map<String, AdEntity> adsMap =
                adPostgresRepository.findByIdIn(missingImpressions)
                        .stream()
                        .collect(Collectors.toMap(
                                AdEntity::getId,
                                Function.identity()
                        ));

        for (String adId : adIds) {
            Long increment = impressions.getOrDefault(adId, 0L);
            AdImpressionEntity existing = existingMap.get(adId);

            if (existing != null) {
                existing.setCount(
                        existing.getCount() + increment
                );
            }else{
                AdEntity ad = adsMap.get(adId);
                if (ad == null) {
                    continue;
                }
                ad.addImpression(today, increment);
            }
        }
    }
}
