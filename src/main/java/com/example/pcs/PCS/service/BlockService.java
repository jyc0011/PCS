package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import com.example.pcs.PCS.domain.Transaction;
import com.example.pcs.PCS.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public Block createBlock(List<Transaction> transactions) {
        int index = getNextIndex();
        long timestamp = System.currentTimeMillis();
        String prevHash = getPreviousHash();
        String proof = calculateProofOfWork();
        String merkleRoot = calculateMerkleRoot(transactions);

        Block block = new Block(index, timestamp, proof, prevHash, transactions, merkleRoot);
        return blockRepository.save(block);
    }

    private int getNextIndex() {
        // Get the index for the new block
        return (int) blockRepository.count() + 1;
    }

    private String getPreviousHash() {
        // Retrieve the last block and use its hash
        Block lastBlock = blockRepository.findTopByOrderByIdDesc();
        return lastBlock != null ? lastBlock.getHash() : "0";
    }

    private String calculateProofOfWork() {
        // Implement your Proof of Work algorithm here
        return "proof";
    }

    private String calculateMerkleRoot(List<Transaction> transactions) {
        // Calculate Merkle Root from transactions
        return "merkle_root";
    }
}