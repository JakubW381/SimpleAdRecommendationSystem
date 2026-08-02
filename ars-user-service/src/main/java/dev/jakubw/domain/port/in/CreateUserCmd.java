package dev.jakubw.domain.port.in;

import dev.jakubw.domain.model.AdUser;
import dev.jakubw.domain.port.in.model.CreateUserCmdRequest;

public interface CreateUserCmd {
    AdUser execute(CreateUserCmdRequest request);
}
