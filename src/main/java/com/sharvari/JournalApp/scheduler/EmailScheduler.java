package com.sharvari.JournalApp.scheduler;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import com.sharvari.JournalApp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmailScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "${journal.reminder.cron}")
    public void sendEveningJournalReminder() {

        log.info("Evening journal reminder job started...");
        List<Users> users = userRepository.findUsersWithEmail();

        for (Users user : users) {
            emailService.sendEmail(
                    user.getEmail(),
                    "📝 Your Evening Journal Reminder",
                    "Hi " + user.getUsername() + ",\n\n" +
                            "Take a few minutes this evening to reflect on your day.\n" +
                            "Writing in your journal helps you grow! ✨\n\n" +
                            "Open your journal and start writing.\n\n" +
                            "- JournalApp"
            );
        }
        log.info("Reminder emails sent to {} users.", users.size());
    }
}
