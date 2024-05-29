package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/contract")
    public String showContract() {
        return "contract/contract";  // src/main/resources/templates/contract/contract.html
    }

    @PostMapping("/contract/execute")
    public ResponseEntity<?> processContract(@RequestParam("image") MultipartFile image,
                                             @RequestParam("signature1") MultipartFile signature1,
                                             @RequestParam("signature2") MultipartFile signature2,
                                             @RequestParam("partyA") String partyA,
                                             @RequestParam("partyB") String partyB) {
        try {
            ImageCompositionResult result = contractService.processContract(image, signature1, signature2, partyA, partyB);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }
}
