package dev.jakubw.adapter.out.cache;

import dev.jakubw.domain.port.out.impression.AdImpressionCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RegisterImpressionRedisAdapter implements AdImpressionCachePort {

    private static final String PREFIX = "impressions:";
    private static final String PROCESSING_PREFIX = "impressions-processing:";

    private final RedisTemplate<String, Object> template;

    @Override
    public void cacheImpression(String adId) {
        template.opsForHash()
                .increment(PREFIX, adId,1);
    }
    @Override
    public Map<String, Long> getImpressions() {
        if (!Boolean.TRUE.equals(template.hasKey(PREFIX))) {
            return Map.of();
        }

        Boolean r = template.renameIfAbsent(PREFIX,PROCESSING_PREFIX);
        if (Boolean.FALSE.equals(r)){
            return Map.of();
        }

        Map<Object,Object> entries = template.opsForHash().entries(PROCESSING_PREFIX);

        return entries.entrySet()
                .stream().collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> Long.parseLong(e.getValue().toString())
                ));
    }

    @Override
    public void clearProcessing() {
        template.delete(PROCESSING_PREFIX);
    }
}
