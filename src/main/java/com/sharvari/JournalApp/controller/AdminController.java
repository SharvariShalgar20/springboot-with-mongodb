package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.cache.AppCache;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Admin-only operations")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> users = userService.getAll();

        if(users != null && !users.isEmpty()) {
            logger.info("Returning {} users to admin", users.size());
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        logger.warn("No users found in the system");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public void createAdmin(@RequestBody Users user){
        userService.saveAdmin(user);
        logger.info("Admin user created: {}", user.getUsername());
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
