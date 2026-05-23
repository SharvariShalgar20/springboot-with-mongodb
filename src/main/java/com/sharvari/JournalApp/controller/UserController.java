package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public void createEntry(@RequestBody Users user) {
        userService.saveEntry(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable String username) {
        Users userInDB = userService.findByUsername(username);

        if(userInDB != null) {
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
