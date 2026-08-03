package dev.jakubw.adapter.in.rest;

import dev.jakubw.adapter.in.dto.AdRecommendationCandidateDto;
import dev.jakubw.domain.port.in.GetRecommendationQry;
import dev.jakubw.domain.port.out.model.RecommendationStrat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/recommendation")
@RestController
@RequiredArgsConstructor
public class GetAdRecommendationController {

    private final GetRecommendationQry query;

    @GetMapping("/{strat}/{count}")
    public ResponseEntity<List<AdRecommendationCandidateDto>> getRecommendation(
            @RequestHeader(value = "X-User-Id", required = true) String user,
            @PathVariable int count,
            @PathVariable RecommendationStrat strat
    ){
        List<AdRecommendationCandidateDto> dto = query.execute(user, count, strat)
                .stream().map(rec -> new AdRecommendationCandidateDto(
                        rec.id(),
                        rec.name(),
                        rec.adUrl()
                )).toList();
        return ResponseEntity.ok(dto);
    }
}
