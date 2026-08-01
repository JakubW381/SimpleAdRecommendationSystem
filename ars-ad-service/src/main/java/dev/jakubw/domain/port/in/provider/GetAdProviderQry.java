package dev.jakubw.domain.port.in.provider;

import dev.jakubw.domain.model.AdProvider;

public interface GetAdProviderQry {
    AdProvider execute(String id);
}
