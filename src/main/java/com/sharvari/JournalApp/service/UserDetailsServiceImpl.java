package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users byUsername = userRepository.findByUsername(username);

        if(byUsername != null) {
            return User.builder()
                    .username(byUsername.getUsername())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRoles() != null
                            ? byUsername.getRoles().toArray(new String[0])
                            : new String[]{"USER"})
                    .build();
        }

        throw new UsernameNotFoundException("User not found! " + username);
    }
}
