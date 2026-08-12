package dev.jakubw.adapter.in.rest.ad;


import dev.jakubw.adapter.in.rest.ad.dto.AdDto;
import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.GetAdsQry;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.api.trace.Span;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ad/ads")
@RequiredArgsConstructor
public class GetAdsController {

    private final GetAdsQry query;
    private final AdMapper adMapper;
    private final Tracer tracer;

    @GetMapping
    public ResponseEntity<List<AdDto>> getAds(
            @RequestHeader(value = "X-Provider-Id", required = true) String providerId
    ){
//
//        Span span = Span.current();
//        log.info("OpenTelemetry Span id "+span.getSpanContext().getSpanId());
//        log.info("OpenTelemetry Trace id "+span.getSpanContext().getTraceId());
//
//        log.info("Micrometer Span trace id "+tracer.currentSpan().context().spanId());
//        log.info("Micrometer Span trace id "+tracer.currentSpan().context().traceId());

        List<Ad> ads = query.execute(providerId);
        List<AdDto> dto = ads.stream().map(adMapper::adToDto).toList();
        return ResponseEntity.ok(dto);
    }
}
