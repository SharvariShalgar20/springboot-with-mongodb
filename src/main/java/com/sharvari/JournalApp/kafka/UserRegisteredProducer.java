package com.sharvari.JournalApp.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharvari.JournalApp.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class UserRegisteredProducer {

    private static final String TOPIC = "user-registered";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishUserRegistered(UserRegisteredEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, message);
            log.info("Published user-registered event for: {}", event.getUsername());
        } catch (Exception e) {
            log.error("Failed to publish user-registered event: {}", e.getMessage());
        }
    }
}
