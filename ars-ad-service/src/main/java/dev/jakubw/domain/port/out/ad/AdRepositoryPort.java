package dev.jakubw.domain.port.out.ad;

import dev.jakubw.domain.model.Ad;

import java.util.List;

public interface AdRepositoryPort {

    Ad createAd(Ad ad, String providerId);
    List<Ad> getAds(String providerId);
}
