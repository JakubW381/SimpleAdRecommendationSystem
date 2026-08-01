package dev.jakubw.adapter.in.rest.impression;

import dev.jakubw.adapter.in.rest.impression.dto.ImpressionDto;
import dev.jakubw.domain.port.in.impression.GetImpressionsQry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/impressions")
@RequiredArgsConstructor
public class GetImpressionsController {

    private final GetImpressionsQry query;
    private final ImpressionMapper impressionMapper;

    // Temporarily providerId will be from the path,
    // but in the future from the header set by gateway after authorization
    @GetMapping("/{providerId}/{adId}")
    public ResponseEntity<List<ImpressionDto>> handle(
            @PathVariable String providerId,
            @PathVariable String adId
    ){
        List<ImpressionDto> dto = query.execute(adId,providerId)
                .stream().map(impressionMapper::impressionToDto).toList();
        return ResponseEntity.ok(dto);
    }
}
