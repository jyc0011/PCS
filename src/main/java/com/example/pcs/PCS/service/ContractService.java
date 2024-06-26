package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.domain.Transaction;
import com.example.pcs.PCS.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;


@Service
public class ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
    @Autowired
    private BlockService blockService;

    public ImageCompositionResult processContract(MultipartFile imageFile, MultipartFile signature1, MultipartFile signature2, String partyA, String partyB) {
        try {
            byte[] compositeImage = ImageUtil.composeImage(imageFile, signature1, signature2, partyA, partyB);
            String base64Image = Base64.getEncoder().encodeToString(compositeImage);
            String imageHash = hashImage(compositeImage);
            Transaction transaction = new Transaction(partyA, partyB, imageHash);
            Block newBlock = blockService.createBlock(Arrays.asList(transaction), partyA, partyB);
            return new ImageCompositionResult(base64Image, imageHash, newBlock);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to process contract", e);
            throw new RuntimeException("Failed to process contract", e);
        }
    }

    private String hashImage(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(data);
        StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
        for (byte b : encodedhash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}