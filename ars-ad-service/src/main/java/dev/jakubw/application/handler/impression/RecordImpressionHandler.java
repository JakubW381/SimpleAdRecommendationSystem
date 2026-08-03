package dev.jakubw.application.handler.impression;

import dev.jakubw.domain.port.in.impression.RecordImpressionCmd;
import dev.jakubw.domain.port.out.impression.AdImpressionCachePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecordImpressionHandler implements RecordImpressionCmd {

    private final AdImpressionCachePort cachePort;

    @Override
    public void execute(String adId) {
        cachePort.cacheImpression(adId);
    }
}
