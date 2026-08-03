package dev.jakubw.domain.port.out.impression;

import java.util.Map;

public interface AdImpressionCachePort {
    void cacheImpression(String impressionId);
    Map<String, Long> getImpressions();
    void clearProcessing();
}
