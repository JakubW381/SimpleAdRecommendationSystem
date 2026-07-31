package dev.jakubw.adapter.in.rest.ad;


import dev.jakubw.adapter.in.rest.ad.dto.AdDto;
import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.GetAdsQry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class GetAdsController {

    private final GetAdsQry query;
    private final AdMapper adMapper;

    @GetMapping("/{providerId}")
    public ResponseEntity<List<AdDto>> getAds(@PathVariable String providerId){
        List<Ad> ads = query.execute(providerId);
        List<AdDto> dto = ads.stream().map(adMapper::adToDto).toList();
        return ResponseEntity.ok(dto);
    }
}
