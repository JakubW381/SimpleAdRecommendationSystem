package dev.jakubw.domain.port.in.provider;

import dev.jakubw.domain.model.AdProvider;

public interface RegisterAdProviderCmd {
    AdProvider execute(String providerName);
}