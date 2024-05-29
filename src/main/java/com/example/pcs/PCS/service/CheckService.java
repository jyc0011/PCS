package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.repository.BlockRepository;
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

    public boolean verifyContract(MultipartFile certificate, String partyA, String partyB) {
        try {
            byte[] certBytes = certificate.getBytes();
            String certificateHash = generateHash(certBytes);
            Block block = blockRepository.findByPartyAAndPartyB(partyA, partyB);
            return block != null && block.getContractHash().equals(certificateHash);
        } catch (Exception e) {
            logger.error("Error verifying contract", e);
            return false;
        }
    }

    private String generateHash(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return HexFormat.of().formatHex(hash);
    }
}
