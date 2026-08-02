package dev.jakubw.application.handler;

import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.in.CreateUserCmd;
import dev.jakubw.domain.port.in.model.CreateUserCmdRequest;
import dev.jakubw.domain.port.out.AdUserRepositoryPort;
import dev.jakubw.domain.port.out.model.CreateUserPortRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateUserHandler implements CreateUserCmd {

    private final AdUserRepositoryPort repositoryPort;

    @Override
    public AdUser execute(CreateUserCmdRequest request) {
        String id = UUID.randomUUID().toString();
        return repositoryPort.createUser(new CreateUserPortRequest(id, request.username(), request.email(), request.tags()));
    }
}
