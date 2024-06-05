package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.domain.User;
import com.example.pcs.PCS.service.UserService;
import com.example.pcs.PCS.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/register")
    public String showsignup() {
        return "user/signup";
    }


    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@ModelAttribute User user) {
        System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword());
        boolean success = userService.registerNewUser(user);
        if (success) {
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Registration failed. Please try again.\"}");
        }
    }

    @GetMapping("/log")
    public String showlogin() {
        System.out.println("loginpage");
        return "user/login";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Assuming authentication is handled by LoginFilter and token is set in the header
        return ResponseEntity.ok().build();  // Simply return OK status, token should be in the header already
    }

    @GetMapping("/findid")
    public String find_id() {
        return "user/findid";
    }

    @GetMapping("/findpwd")
    public String find_pwd() {
        return "user/findpw";
    }

}

