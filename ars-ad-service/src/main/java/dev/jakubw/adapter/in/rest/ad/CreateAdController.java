package dev.jakubw.adapter.in.rest.ad;

import dev.jakubw.adapter.in.rest.ad.dto.AdDto;
import dev.jakubw.adapter.in.rest.ad.dto.CreateAdRequest;
import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.CreateAdCmd;
import dev.jakubw.domain.port.in.ad.model.CreateAdCmdDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ad/ads")
@RequiredArgsConstructor
public class CreateAdController {

    private final CreateAdCmd command;
    private final AdMapper adMapper;

    @PostMapping("/add")
    public ResponseEntity<AdDto> createAd(
            @Valid @RequestBody CreateAdRequest request,
            @RequestHeader(value = "X-Provider-Id", required = true) String providerId
    ) {
        Ad ad = command.execute(new CreateAdCmdDto(
                        providerId,
                        request.name(),
                        request.adUrl(),
                        request.campaignEnd(),
                        request.maxDayCount(),
                        request.tags()
                )
        );
        AdDto dto = adMapper.adToDto(ad);
        return ResponseEntity.ok(dto);
    }
}
