package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.model.JournalEntry;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.JournalEntryService;
import com.sharvari.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesByUser(@PathVariable String username) {
        Users byUsername = userService.findByUsername(username);
        List<JournalEntry> entries = byUsername.getJournalEntryList();
        if (entries == null || entries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        ResponseEntity<List<JournalEntry>> ok = ResponseEntity.ok(entries);
        return ok;
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        try {
            journalEntryService.saveEntry(journalEntry, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId, @PathVariable String username) {

        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {

        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if(old == null) {
            return ResponseEntity.notFound().build();
        }

        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());

        old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());


        journalEntryService.saveEntry(old);
        return ResponseEntity.ok(old);
    }
}
