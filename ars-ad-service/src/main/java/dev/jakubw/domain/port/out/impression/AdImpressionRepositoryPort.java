package dev.jakubw.domain.port.out.impression;

import dev.jakubw.domain.model.AdDailyImpression;

import java.util.List;

public interface AdImpressionRepositoryPort {
    List<AdDailyImpression> findByAdIdAndProviderId(String adId, String providerId);
}
