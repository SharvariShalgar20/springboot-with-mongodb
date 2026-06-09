package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.event.UserRegisteredEvent;
import com.sharvari.JournalApp.kafka.UserRegisteredProducer;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired(required = false)
    private UserRegisteredProducer userRegisteredProducer;

    public void saveEntry(Users user) {

        try {
            userRepository.save(user);
            logger.info("User saved successfully: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Error saving user: {}. Reason: {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }

    public void saveNewUser(Users user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            logger.info("New user registered successfully: {}", user.getUsername());

            if (userRegisteredProducer != null
                    && user.getEmail() != null
                    && !user.getEmail().isEmpty()) {
                UserRegisteredEvent event = new UserRegisteredEvent(
                        user.getUsername(),
                        user.getEmail()
                );
                userRegisteredProducer.publishUserRegistered(event);
            }

        } catch (Exception e) {
            logger.error("Failed to register new user: {}. Reason: {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }

    public List<Users> getAll() {
        List<Users> users = userRepository.findAll();
        logger.debug("Total users found: {}", users.size());
        return users;
    }

    public Optional<Users> findUserById(ObjectId myId) {
        Optional<Users> user = userRepository.findById(myId);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", myId);
        } else {
            logger.warn("No user found with ID: {}", myId);
        }
        return user;
    }

    public void deleteUserById(ObjectId myId) {
        userRepository.deleteById(myId);
        logger.info("User deleted successfully. ID: {}", myId);
    }

    public Users findByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("User found: {}", username);
        } else {
            logger.warn("No user found with username: {}", username);
        }
        return user;
    }

    public void saveAdmin(Users user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
            logger.info("Admin user created successfully: {}", user.getUsername());
        } catch ( Exception e) {
            logger.error("Failed to create admin user: {}. Reason: {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }

}
