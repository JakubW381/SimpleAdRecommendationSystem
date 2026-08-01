package dev.jakubw.adapter.in.rest.provider;

import dev.jakubw.adapter.in.rest.provider.dto.CreateProviderRequest;
import dev.jakubw.adapter.in.rest.provider.dto.ProviderDto;
import dev.jakubw.application.handler.provider.RegisterAdProviderHandler;
import dev.jakubw.domain.model.AdProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class RegisterAdProviderController {

    private final RegisterAdProviderHandler handler;
    private final ProviderMapper providerMapper;

    @PostMapping("/register")
    public ResponseEntity<ProviderDto> handle(@Valid @RequestBody CreateProviderRequest request){
        AdProvider provider = handler.execute(request.name());
        ProviderDto dto = providerMapper.providerToDto(provider);
        return ResponseEntity.ok(dto);
    }
}
