package dev.jakubw.adapter.in.messaging;

import dev.jakubw.adapter.in.messaging.events.ImpressionRegisterEvent;
import dev.jakubw.domain.port.in.impression.RecordImpressionCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class ImpressionListener {

    private final RecordImpressionCmd command;

    @KafkaListener(
        groupId = "adServiceImpressionListenerGroup", topics = "impressions"

    )
    public void impressionListener(ImpressionRegisterEvent event){
    }
}
