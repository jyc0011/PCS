package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Controller
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/contract")
    public String showContract() {
        return "contract/contract";  // src/main/resources/templates/contract/contract.html
    }

    @PostMapping("/contract/execute")
    public ResponseEntity<byte[]> processContractAndReturnImage(@RequestParam("image") MultipartFile image,
                                                                @RequestParam("signature1") MultipartFile signature1,
                                                                @RequestParam("signature2") MultipartFile signature2,
                                                                @RequestParam("partyA") String partyA,
                                                                @RequestParam("partyB") String partyB) {
        try {
            // 이미지 처리 로직
            ImageCompositionResult result = contractService.processContract(image, signature1, signature2, partyA, partyB);
            byte[] imageData = Base64.getDecoder().decode(result.getBase64Image());

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
