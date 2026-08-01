package dev.jakubw.domain.port.in.ad;

import dev.jakubw.domain.model.Ad;
import dev.jakubw.domain.port.in.ad.model.CreateAdCmdDto;

public interface CreateAdCmd {
    Ad execute(CreateAdCmdDto cmd);
}