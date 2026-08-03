package dev.jakubw.domain.port.out.ad;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.model.AdStatus;
import dev.jakubw.domain.port.out.ad.model.AdRecommendationCandidate;

import java.util.List;

public interface AdRepositoryPort {

    Ad createAd(Ad ad, String providerId);
    List<Ad> getAds(String providerId);
    List<AdRecommendationCandidate> getRecommendationCandidates();
}
