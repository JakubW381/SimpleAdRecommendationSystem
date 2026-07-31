package dev.jakubw.adapter.in.rest.provider;

import dev.jakubw.adapter.in.rest.provider.dto.ProviderDto;
import dev.jakubw.domain.model.AdProvider;
import dev.jakubw.domain.port.in.provider.GetAdProviderQry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/provider")
public class GetAdProviderController {

    private final GetAdProviderQry query;
    private final ProviderMapper providerMapper;

    @GetMapping("/{providerId}")
    public ResponseEntity<ProviderDto> handle(@PathVariable String providerId){
        AdProvider provider = query.execute(providerId);
        ProviderDto dto = providerMapper.providerToDto(provider);
        return ResponseEntity.ok(dto);
    }
}
