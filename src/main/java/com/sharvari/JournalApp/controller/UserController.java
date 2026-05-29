package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.api.response.WeatherResponse;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import com.sharvari.JournalApp.service.UserService;
import com.sharvari.JournalApp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users userInDB = userService.findByUsername(username);

        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveEntry(userInDB);

        logger.info("User profile updated successfully for: {}", username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(authentication.getName());
        logger.info("User account deleted successfully: {}", username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping
    public ResponseEntity<?> getWeatherWithUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Pune", "India");
        String username = authentication.getName();

        String greetings = "";
        if(weather != null) {
            greetings = " Today's Weather is " + weather.getWeather().get(0).getDescription();
        }
        return new ResponseEntity<>("Hi " + username + greetings , HttpStatus.OK);
    }
}
