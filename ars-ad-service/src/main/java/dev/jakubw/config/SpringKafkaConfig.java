package dev.jakubw.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SpringKafkaConfig {
    @Bean
    NewTopic impressionTopic(){
        return TopicBuilder.name("impressions")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
