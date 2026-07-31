package dev.jakubw.application.handler.ad;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.model.AdStatus;
import dev.jakubw.domain.port.in.ad.CreateAdCmd;
import dev.jakubw.domain.port.in.ad.model.CreateAdCmdDto;
import dev.jakubw.domain.port.out.ad.AdRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateAdHandler implements CreateAdCmd {

    private final AdRepositoryPort adRepositoryPort;

    @Override
    public Ad execute(CreateAdCmdDto cmd) {

        Ad ad = new Ad(
                UUID.randomUUID().toString(),
                cmd.name(),
                cmd.adUrl(),
                cmd.campaignEnd(),
                cmd.maxDayCount(),
                AdStatus.ACTIVE
        );

        return adRepositoryPort.createAd(ad, cmd.providerId());
    }
}
