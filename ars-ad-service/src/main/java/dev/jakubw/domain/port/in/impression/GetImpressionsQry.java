package dev.jakubw.domain.port.in.impression;

import dev.jakubw.domain.model.AdDailyImpression;

import java.util.List;

public interface GetImpressionsQry {
    List<AdDailyImpression> execute(String adId, String providerId);
}
