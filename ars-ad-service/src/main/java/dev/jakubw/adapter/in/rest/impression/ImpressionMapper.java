package dev.jakubw.adapter.in.rest.impression;

import dev.jakubw.adapter.in.rest.impression.dto.ImpressionDto;
import dev.jakubw.domain.model.AdDailyImpression;
import org.springframework.stereotype.Component;

@Component
public class ImpressionMapper {
    public ImpressionDto impressionToDto(AdDailyImpression impression){
        return new ImpressionDto(impression.getId(),impression.getDay(), impression.getCount());
    }
}
