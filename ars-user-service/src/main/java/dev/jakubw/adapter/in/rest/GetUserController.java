package dev.jakubw.adapter.in.rest;

import dev.jakubw.adapter.in.dto.AdUserDto;
import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.in.GetUserCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GetUserController {

    private final GetUserCmd command;

    @GetMapping("/{userId}")
    public ResponseEntity<AdUserDto> handle(@PathVariable String userId){
        AdUser user = command.execute(userId);
        AdUserDto dto = new AdUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getTags()
        );
        return ResponseEntity.ok(dto);
    }
}
