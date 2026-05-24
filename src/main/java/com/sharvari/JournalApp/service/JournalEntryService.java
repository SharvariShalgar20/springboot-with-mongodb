package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.JournalEntry;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.JournalEntryRepository;
import com.sharvari.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username)
    {
        Users user = userService.findByUsername(username);
        JournalEntry save = journalEntryRepository.save(journalEntry);
        user.getJournalEntryList().add(save);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteById(ObjectId myId, String username) {
        Users user = userService.findByUsername(username);
        user.getJournalEntryList().removeIf(x -> x.getId().equals(myId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(myId);
    }
}
