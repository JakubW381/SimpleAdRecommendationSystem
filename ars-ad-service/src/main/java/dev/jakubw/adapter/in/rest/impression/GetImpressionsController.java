package dev.jakubw.adapter.in.rest.impression;

import dev.jakubw.adapter.in.rest.impression.dto.ImpressionDto;
import dev.jakubw.domain.port.in.impression.GetImpressionsQry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ad/impression")
@RequiredArgsConstructor
public class GetImpressionsController {

    private final GetImpressionsQry query;
    private final ImpressionMapper impressionMapper;

    @GetMapping("/{adId}")
    public ResponseEntity<List<ImpressionDto>> handle(
            @RequestHeader(value = "X-Provider-Id", required = true) String providerId,
            @PathVariable String adId
    ){
        List<ImpressionDto> dto = query.execute(adId,providerId)
                .stream().map(impressionMapper::impressionToDto).toList();
        return ResponseEntity.ok(dto);
    }
}
