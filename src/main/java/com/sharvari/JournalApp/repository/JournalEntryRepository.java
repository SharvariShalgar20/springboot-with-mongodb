package com.sharvari.JournalApp.repository;

import com.sharvari.JournalApp.model.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
