package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.VerificationResponse;
import com.example.pcs.PCS.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CheckController {
    @Autowired
    private CheckService checkService;

    @GetMapping("/check")
    public String showCheck() {
        return "check/check";  // src/main/resources/templates/check/check.html
    }

    @PostMapping("/check/verify")
    public ResponseEntity<VerificationResponse> verifyContract(
            @RequestParam("certificate") MultipartFile certificate,
            @RequestParam("image") MultipartFile image) {
        boolean isMatch = checkService.verifyContract(certificate, image);
        return ResponseEntity.ok(new VerificationResponse(isMatch));
    }
}

