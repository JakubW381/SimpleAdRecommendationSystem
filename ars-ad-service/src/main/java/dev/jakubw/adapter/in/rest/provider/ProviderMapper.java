package dev.jakubw.adapter.in.rest.provider;

import dev.jakubw.adapter.in.rest.ad.AdMapper;
import dev.jakubw.adapter.in.rest.provider.dto.ProviderDto;
import dev.jakubw.domain.model.AdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProviderMapper {

    private final AdMapper adMapper;

    public ProviderDto providerToDto(AdProvider provider){
        return new ProviderDto(
                provider.getId(),
                provider.getName(),
                provider.getAds().stream().map(adMapper::adToDto).toList(),
                provider.getCreationDate()
        );
    }
}
