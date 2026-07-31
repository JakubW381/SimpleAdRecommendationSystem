package dev.jakubw.domain.port.in.ad;

import dev.jakubw.domain.model.Ad;

import java.util.List;

public interface GetAdsQry {
    List<Ad> execute(String providerId);
}
