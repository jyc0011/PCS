package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.User;
import com.example.pcs.PCS.service.UserService;
import com.example.pcs.PCS.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            System.out.println("success");
            return ResponseEntity.ok().body(new AuthenticationResponse(token, "Login successful."));
        }
        System.out.println("Fail");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(null, "Authentication failed."));
    }

    class AuthenticationResponse {
        private String jwt;
        private String message;

        public AuthenticationResponse(String jwt, String message) {
            this.jwt = jwt;
            this.message = message;
        }

    }

}

