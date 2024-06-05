package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() { return "home/home";}

    @GetMapping("/about")
    public String about() { return "home/about";}
    @GetMapping("/member")
    public String member() { return "home/member";}

}