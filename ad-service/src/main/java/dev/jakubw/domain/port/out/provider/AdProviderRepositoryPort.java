package dev.jakubw.domain.port.out.provider;

import dev.jakubw.domain.model.AdProvider;

public interface AdProviderRepositoryPort {

    AdProvider registerProvider(AdProvider provider);
    AdProvider getProvider(String id);
}
