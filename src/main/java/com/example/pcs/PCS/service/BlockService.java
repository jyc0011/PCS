package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.domain.Transaction;
import com.example.pcs.PCS.repository.BlockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public Block createBlock(List<Transaction> transactions, String partyA, String partyB) {
        int index = getNextIndex();
        long timestamp = System.currentTimeMillis();
        String prevHash = getPreviousHash();
        String proof = calculateProofOfWork(prevHash);
        String merkleRoot = calculateMerkleRoot(transactions);
        String blockHash=hashBlock(index, timestamp, proof, prevHash, merkleRoot);
        Block block = new Block(index, blockHash, timestamp, proof, prevHash, transactions, merkleRoot, partyA, partyB);
        return blockRepository.save(block);
    }

    private int getNextIndex() {
        return (int) blockRepository.count() + 1;
    }

    private String getPreviousHash() {
        Block lastBlock = blockRepository.findTopByOrderByIdDesc();
        return lastBlock != null ? lastBlock.getBlockHash() : "0";
    }

    private String calculateProofOfWork(String previousProof) {
        long proof = 0;
        while (!isValidProof(previousProof, Long.toString(proof))) {
            proof++;
        }
        return Long.toString(proof);
    }

    private boolean isValidProof(String previousProof, String proof) {
        String guess = previousProof + proof;
        String result = hash(guess);
        return result.startsWith("0000");
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateMerkleRoot(List<Transaction> transactions) {
        List<String> treeLayer = new ArrayList<>();
        for (Transaction transaction : transactions) {
            treeLayer.add(transaction.getImgHash());
        }

        List<String> newTreeLayer = treeLayer;
        while (newTreeLayer.size() > 1) {
            newTreeLayer = new ArrayList<>();
            for (int i = 0; i < treeLayer.size(); i += 2) {
                String left = treeLayer.get(i);
                String right = i+1 < treeLayer.size() ? treeLayer.get(i+1) : left;
                newTreeLayer.add(hash(left + right));
            }
            treeLayer = newTreeLayer;
        }

        return treeLayer.size() == 1 ? treeLayer.get(0) : "";
    }
    private String hashBlock(int index, long timestamp, String proof, String prevHash, String merkleRoot) {
        String input = index + timestamp + proof + prevHash + merkleRoot;
        return hash(input);
    }
}