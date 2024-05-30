package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.domain.Transaction;
import com.example.pcs.PCS.repository.BlockRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class CheckService {
    private static final Logger logger = LoggerFactory.getLogger(CheckService.class);

    @Autowired
    private BlockRepository blockRepository;

    public boolean verifyContract(MultipartFile certificate, MultipartFile image) {
        try {
            // PDF에서 인덱스 읽기
            int blockIndex = extractIndexFromPDF(certificate);
            Block block = blockRepository.findById((long) blockIndex).orElse(null);
            if (block == null) {
                return false;
            }

            // 이미지 해시 생성 및 비교
            byte[] imageBytes = image.getBytes();
            String imageHash = generateHash(imageBytes);
            System.out.println(imageBytes);
            System.out.println(imageHash);
            boolean isMatch = false;
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.getImgHash().equals(imageHash)) {
                    isMatch = true;
                    break;
                }
            }
            return isMatch;
        } catch (Exception e) {
            logger.error("Error verifying contract", e);
            return false;
        }
    }

    private int extractIndexFromPDF(MultipartFile certificate) throws Exception {
        PDDocument document = PDDocument.load(certificate.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.startsWith("Index: ")) {
                return Integer.parseInt(line.replace("Index: ", "").trim());
            }
        }
        document.close();
        throw new IllegalArgumentException("Index not found in the PDF");
    }

    private String generateHash(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return HexFormat.of().formatHex(hash);
    }
}