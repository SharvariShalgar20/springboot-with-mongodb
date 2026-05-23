package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(Users user) {
        userRepository.save(user);
    }

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public Optional<Users> findUserById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteUserById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
