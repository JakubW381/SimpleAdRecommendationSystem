package dev.jakubw.application.handler.ad;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.GetAdsQry;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAdsHandler implements GetAdsQry {

    private final AdRepositoryPort adRepositoryPort;

    @Override
    public List<Ad> execute(String providerId) {
        return adRepositoryPort.getAds(providerId);
    }
}
