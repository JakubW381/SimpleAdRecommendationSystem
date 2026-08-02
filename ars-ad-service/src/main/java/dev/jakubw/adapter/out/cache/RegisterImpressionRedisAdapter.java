package dev.jakubw.adapter.out.cache;

import dev.jakubw.domain.port.in.impression.RecordImpressionCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class RegisterImpressionRedisAdapter {

    private final RecordImpressionCmd command;
    private final RedisTemplate<String,Object> template;





}
