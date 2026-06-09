package com.sharvari.JournalApp.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharvari.JournalApp.event.UserRegisteredEvent;
import com.sharvari.JournalApp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class UserRegisteredConsumer {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "user-registered", groupId = "journal-app-group")
    public void handleUserRegistered(String message) {
        try {
            UserRegisteredEvent event = objectMapper
                    .readValue(message, UserRegisteredEvent.class);

            log.info("Consumed user-registered event for: {}", event.getUsername());

            if (event.getEmail() != null && !event.getEmail().isEmpty()) {
                emailService.sendEmail(
                        event.getEmail(),
                        "Welcome to JournalApp! 📖",
                        "Hi " + event.getUsername() + ",\n\n" +
                                "Welcome to JournalApp! Your account has been created.\n\n" +
                                "Start writing your first journal entry today!\n\n" +
                                "- JournalApp Team"
                );
                log.info("Welcome email sent to: {}", event.getEmail());
            }
        } catch (Exception e) {
            log.error("Failed to process user-registered event: {}", e.getMessage());
        }
    }
}
