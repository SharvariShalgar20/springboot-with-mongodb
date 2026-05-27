package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    void saveNewUser_shouldEncodePassword() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        userService.saveNewUser(user);

        assertNotEquals("plainPassword", user.getPassword());
        System.out.println("Encoded password: " + user.getPassword());
    }
}
