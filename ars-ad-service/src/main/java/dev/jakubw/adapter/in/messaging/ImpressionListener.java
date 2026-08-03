package dev.jakubw.adapter.in.messaging;

import dev.jakubw.adapter.in.messaging.events.ImpressionRegisterEvent;
import dev.jakubw.domain.port.in.impression.RecordImpressionCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImpressionListener {

    private final RecordImpressionCmd command;

    @KafkaListener(
        groupId = "adServiceImpressionListenerGroup", topics = "impressions"
    )
    public void impressionListener(ImpressionRegisterEvent event){
        log.info("Impression Event: ", event);
        command.execute(event.adId());
    }
}
