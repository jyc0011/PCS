package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.User;
import com.example.pcs.PCS.service.UserService;
import com.example.pcs.PCS.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/register")
    public String showsignup() {
        return "user/signup";  // src/main/resources/templates/check/check.html
    }


    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@ModelAttribute User user) {
        boolean success = userService.registerNewUser(user);
        if (success) {
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Registration failed. Please try again.\"}");
        }
    }

    @GetMapping("/log")
    public String showlogin() {
        return "user/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(authenticatedUser.getUsername());
            return ResponseEntity.ok().body(new AuthenticationResponse(token));
        }
        return ResponseEntity.status(401).body("Authentication failed");
    }
    class AuthenticationResponse {
        private String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }
    }
}

