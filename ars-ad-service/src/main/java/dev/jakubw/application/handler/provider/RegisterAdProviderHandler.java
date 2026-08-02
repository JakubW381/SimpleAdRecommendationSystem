package dev.jakubw.application.handler.provider;

import dev.jakubw.domain.model.AdProvider;
import dev.jakubw.domain.port.in.provider.RegisterAdProviderCmd;
import dev.jakubw.domain.port.out.provider.AdProviderRepositoryPort;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterAdProviderHandler implements RegisterAdProviderCmd {

    private final AdProviderRepositoryPort adProviderRepositoryPort;

    public RegisterAdProviderHandler(AdProviderRepositoryPort adProviderRepositoryPort) {
        this.adProviderRepositoryPort = adProviderRepositoryPort;
    }

    @Override
    public AdProvider execute(String providerName) {

        AdProvider provider = new AdProvider(
                UUID.randomUUID().toString(),
                providerName,
                LocalDateTime.now()
        );

        return adProviderRepositoryPort.registerProvider(provider);
    }
}
