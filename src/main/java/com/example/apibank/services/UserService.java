package com.example.apibank.services;

import com.example.apibank.model.User;
import com.example.apibank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Check if the user already exists
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        // Actions done if the user is not located
        return userRepository.findById(id).orElse(null);
    }
}

