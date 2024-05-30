package com.example.pcs.PCS.controller;

import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.service.ContractService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/contract")
    public String showContract() {
        return "contract/contract";
    }

    @PostMapping("/contract/execute")
    public ResponseEntity<Map<String, String>> processContractAndReturnFiles(
            @RequestParam("image") MultipartFile image,
            @RequestParam("signature1") MultipartFile signature1,
            @RequestParam("signature2") MultipartFile signature2,
            @RequestParam("partyA") String partyA,
            @RequestParam("partyB") String partyB) {
        try {
            ImageCompositionResult result = contractService.processContract(image, signature1, signature2, partyA, partyB);
            byte[] imageData = Base64.getDecoder().decode(result.getBase64Image());
            byte[] pdfData = createPDF(result, imageData);

            Map<String, String> body = new HashMap<>();
            body.put("image", Base64.getEncoder().encodeToString(imageData));
            body.put("pdf", Base64.getEncoder().encodeToString(pdfData));

            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] createPDF(ImageCompositionResult result, byte[] imageData) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(70, 700);
        contentStream.showText("Block Information:");
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Index: " + result.getBlock().getBIndex());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Block Hash: " + result.getBlock().getBlockHash());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Timestamp: " + result.getBlock().getTimestamp());
        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }
}