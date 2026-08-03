package dev.jakubw.domain.port.out.impression;

import dev.jakubw.domain.model.AdDailyImpression;

import java.util.List;
import java.util.Map;

public interface AdImpressionRepositoryPort {
    List<AdDailyImpression> findByAdIdAndProviderId(String adId, String providerId);
    void persistImpressions(Map<String, Long> impressions);
}
