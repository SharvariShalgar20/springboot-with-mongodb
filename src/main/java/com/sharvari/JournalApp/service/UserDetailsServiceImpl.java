package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users byUsername = userRepository.findByUsername(username);

        if(byUsername != null) {
            logger.info("User '{}' found. Roles: {}", username, byUsername.getRoles());
            return User.builder()
                    .username(byUsername.getUsername())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRoles() != null
                            ? byUsername.getRoles().toArray(new String[0])
                            : new String[]{"USER"})
                    .build();
        }
        logger.warn("Login failed - user not found: {}", username);
        throw new UsernameNotFoundException("User not found! " + username);
    }
}
