package dev.jakubw.application.handler;

import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.in.GetUserCmd;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class GetUserHandler implements GetUserCmd {

    private final AdUserRepositoryPort adUserRepositoryPort;
    @Override
    public AdUser execute(String userId) {
        return adUserRepositoryPort.findUserById(userId);
    }
}
