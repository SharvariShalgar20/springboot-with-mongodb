package com.sharvari.JournalApp.repository;

import com.sharvari.JournalApp.model.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, ObjectId> {
    Users findByUsername(String username);
}
