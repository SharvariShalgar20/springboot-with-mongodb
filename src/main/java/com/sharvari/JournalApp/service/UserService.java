package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(Users user) {
        userRepository.save(user);
    }

    public void saveNewUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
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

    public void saveAdmin(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

}
