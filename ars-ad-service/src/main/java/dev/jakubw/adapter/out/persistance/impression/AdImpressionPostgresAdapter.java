package dev.jakubw.adapter.out.persistance.impression;

import dev.jakubw.domain.model.AdDailyImpression;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class AdImpressionPostgresAdapter implements AdImpressionRepositoryPort {

    private final AdImpressionPostgresRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AdDailyImpression> findByAdIdAndProviderId(String adId, String providerId) {
        List<AdImpressionEntity> entities = repository.findByAdIdAndAdProviderId(adId,providerId);
        return entities.stream().map(entity -> new AdDailyImpression(
                entity.getId(),
                entity.getDay(),
                entity.getCount()
        )).toList();
    }
}
