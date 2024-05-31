package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.User;
import com.example.pcs.PCS.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null || userRepository.findByEmail(user.getEmail()) != null) {
            logger.error("User registration failed: Username or email already exists.");
            return false;
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error saving user: ", e);
            return false;
        }
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
