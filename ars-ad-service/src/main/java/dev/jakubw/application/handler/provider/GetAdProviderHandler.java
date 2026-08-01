package dev.jakubw.application.handler.provider;

import dev.jakubw.domain.model.AdProvider;
import dev.jakubw.domain.port.in.provider.GetAdProviderQry;
import dev.jakubw.domain.port.out.provider.AdProviderRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAdProviderHandler implements GetAdProviderQry {

    private final AdProviderRepositoryPort adProviderRepositoryPort;

    @Override
    public AdProvider execute(String id) {
        return adProviderRepositoryPort.getProvider(id);
    }
}
