package com.example.springboot.service;

import com.example.springboot.entity.User;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByFirstName(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User: " + username + " not found!");

        return new PersonDetails(user.get());
    }
}
