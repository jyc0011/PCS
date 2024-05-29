package com.example.pcs.PCS.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int index;
    private long timestamp;
    private String proof;  // Proof of work
    private String prevHash;  // Previous block hash
    private String hash;  // Current block hash
    private String merkleRoot;
    private String partyA;   // 추가
    private String partyB;   // 추가

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private List<Transaction> transactions;

    // No-arg constructor
    public Block() {
    }

    // Constructor with parameters
    public Block(int index, long timestamp, String proof, String prevHash, List<Transaction> transactions, String merkleRoot, String partyA, String partyB) {
        this.index = index;
        this.timestamp = timestamp;
        this.proof = proof;
        this.prevHash = prevHash;
        this.transactions = transactions;
        this.merkleRoot = merkleRoot;
        this.partyA = partyA;   // 초기화
        this.partyB = partyB;   // 초기화
    }

    public Block(int index, long timestamp, String proof, String prevHash, List<Transaction> transactions, String merkleRoot) {
    }

    public Object getContractHash() {
        return null;
    }
}
