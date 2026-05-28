package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.JournalEntry;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.JournalEntryRepository;
import com.sharvari.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //@Transactional
    public void saveEntry(JournalEntry journalEntry, String username)
    {
        try {
            Users user = userService.findByUsername(username);
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntryList().add(save);
            userService.saveEntry(user);
            logger.info("Journal entry created successfully. ID: {} for user: {}", save.getId(), username);

        } catch (Exception e) {
            logger.error("Failed to create journal entry for user: {}. Reason: {}", username, e.getMessage());
            throw e;
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
            logger.info("Journal entry updated successfully. ID: {}", journalEntry.getId());
        } catch (Exception e) {
            logger.error("Failed to update journal entry. ID: {}. Reason: {}", journalEntry.getId(), e.getMessage());
            throw e;
        }
    }

    public List<JournalEntry> getAll() {
        List<JournalEntry> entries = journalEntryRepository.findAll();
        logger.debug("Total journal entries found: {}", entries.size());
        return entries;
    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        Optional<JournalEntry> entry = journalEntryRepository.findById(myId);
        if (entry.isPresent()) {
            logger.info("Journal entry found. ID: {}", myId);
        } else {
            logger.warn("No journal entry found with ID: {}", myId);
        }
        return entry;
    }

    public void deleteById(ObjectId myId, String username) {
        try {
            Users user = userService.findByUsername(username);
            boolean b = user.getJournalEntryList().removeIf(x -> x.getId().equals(myId));
            if(b) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(myId);
                logger.info("Journal entry deleted successfully. ID: {} for user: {}", myId, username);
            } else {
                logger.warn("Journal entry ID: {} not found in journal list of user: {}", myId, username);
            }
        } catch(Exception e) {
            logger.error("Error deleting journal entry ID: {} for user: {}. Reason: {}", myId, username, e.getMessage());
            throw e;
        }

    }

}
