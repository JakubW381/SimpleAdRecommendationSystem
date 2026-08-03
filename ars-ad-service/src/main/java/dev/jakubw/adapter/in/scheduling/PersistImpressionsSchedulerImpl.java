package dev.jakubw.adapter.in.scheduling;

import dev.jakubw.domain.port.in.impression.scheduller.PersistImpressionsScheduler;
import dev.jakubw.domain.port.out.impression.AdImpressionCachePort;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PersistImpressionsSchedulerImpl implements PersistImpressionsScheduler {

    private final AdImpressionCachePort adImpressionCachePort;
    private final AdImpressionRepositoryPort adImpressionRepositoryPort;

    @Override
    @Scheduled(fixedRate = 10_000)
    public void persist() {
        Map<String,Long> impresisonMap = adImpressionCachePort.getImpressions();
        log.info("Persisting {} impressions. Count of first {}", impresisonMap.size(), impresisonMap.values().stream().findFirst());
        if (impresisonMap.isEmpty()){
            return;
        }
        adImpressionRepositoryPort.persistImpressions(impresisonMap);
        adImpressionCachePort.clearProcessing();
    }
}
