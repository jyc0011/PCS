package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.domain.ImageCompositionResult;
import com.example.pcs.PCS.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
    @Autowired
    private BlockService blockService;

    public ImageCompositionResult processContract(MultipartFile imageFile, MultipartFile signature1, MultipartFile signature2, String partyA, String partyB) {
        try {
            // 이미지 합성 로직
            byte[] compositeImage = ImageUtil.composeImage(imageFile, signature1, signature2);
            // 이미지 이진 데이터화
            String base64Image = Base64.getEncoder().encodeToString(compositeImage);
            // 데이터 해시화
            String hash = hashImage(base64Image.getBytes());
            // 머클 트리 및 블록 생성
            Block newBlock = blockService.createBlock(hash, partyA, partyB);
            return new ImageCompositionResult(base64Image, hash, newBlock);
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
