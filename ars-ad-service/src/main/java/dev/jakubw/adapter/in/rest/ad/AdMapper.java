package dev.jakubw.adapter.in.rest.ad;

import dev.jakubw.adapter.in.rest.ad.dto.AdDto;
import dev.jakubw.domain.model.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdMapper {

    public AdDto adToDto(Ad ad){
        return new AdDto(
                ad.getId(),
                ad.getName(),
                ad.getAdUrl(),
                ad.getCampaignEnd(),
                ad.getMaxDayCount(),
                ad.getStatus(),
                ad.getTags()
        );
    }
}
