package dev.jakubw.adapter.in.rest.ad;


import dev.jakubw.adapter.in.rest.ad.dto.AdDto;
import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.GetAdsQry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ad/ads")
@RequiredArgsConstructor
public class GetAdsController {

    private final GetAdsQry query;
    private final AdMapper adMapper;

    @GetMapping
    public ResponseEntity<List<AdDto>> getAds(
            @RequestHeader(value = "X-Provider-Id", required = true) String providerId
    ){
        List<Ad> ads = query.execute(providerId);
        List<AdDto> dto = ads.stream().map(adMapper::adToDto).toList();
        return ResponseEntity.ok(dto);
    }
}
