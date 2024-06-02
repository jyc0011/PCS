package com.example.pcs.PCS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() { return "home/home";}

    @GetMapping("/about")
    public String about() { return "home/about";}
    @GetMapping("/member")
    public String member() { return "home/member";}
}