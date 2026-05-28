package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createEntry(@RequestBody Users user) {
        userService.saveNewUser(user);
        logger.info("New user registered via public endpoint: {}", user.getUsername());
    }
}
