package com.example.pcs.PCS.service;

import com.example.pcs.PCS.domain.Block;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    public Block createBlock(String hash, String partyA, String partyB) {
        // Correctly passing parameters to the Block constructor
        return new Block(hash, partyA, partyB);
    }
}
