package dev.jakubw.application.handler.impression;

import dev.jakubw.domain.model.AdDailyImpression;
import dev.jakubw.domain.port.in.impression.GetImpressionsQry;
import dev.jakubw.domain.port.out.impression.AdImpressionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAdImpressionsHandler implements GetImpressionsQry {

    private final AdImpressionRepositoryPort adImpressionRepositoryPort;

    @Override
    public List<AdDailyImpression> execute(String adId, String providerId) {
        return adImpressionRepositoryPort.findByAdIdAndProviderId(adId,providerId);
    }
}
