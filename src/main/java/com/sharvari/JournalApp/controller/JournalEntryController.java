package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.model.JournalEntry;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.JournalEntryService;
import com.sharvari.JournalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Journal Entries", description = "CRUD operations for journal entries")
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryController.class);

    @Autowired
    private JournalEntryService journalEntryService;
    
    @Autowired
    private UserService userService;

    @Operation(summary = "Get all journal entries for logged-in user")
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users byUsername = userService.findByUsername(username);
        List<JournalEntry> entries = byUsername.getJournalEntryList();
        if (entries == null || entries.isEmpty()) {
            logger.info("No journal entries found for user: {}", username);
            return ResponseEntity.noContent().build();
        }
        logger.info("Found {} journal entries for user: {}", entries.size(), username);
        return ResponseEntity.ok(entries);
    }

    @Operation(summary = "Get Entry of Journal with id")
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users byUsername = userService.findByUsername(username);
        List<JournalEntry> collect = byUsername.getJournalEntryList().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(collect != null) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

            if(journalEntry.isPresent()) {
                logger.info("Journal entry found. ID: {} for user: {}", myId, username);
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        logger.warn("Journal entry not found. ID: {} for user: {}", myId, username);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a new journal entry")
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(journalEntry, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
        } catch (Exception e) {
            logger.error("Failed to create journal entry for user. Reason: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Operation(summary = "delete journal entry")
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        journalEntryService.deleteById(myId, username);

        logger.info("Delete operation completed for entry ID: {} by user: {}", myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update journal entry")
    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users byUsername = userService.findByUsername(username);

        List<JournalEntry> collect = byUsername.getJournalEntryList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if (collect.isEmpty()) {
            logger.warn("Update failed - entry ID: {} not in journal list of user: {}", id, username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if(old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());

        journalEntryService.saveEntry(old);
        logger.info("Journal entry updated successfully. ID: {} by user: {}", id, username);
        return ResponseEntity.ok(old);
    }
}
