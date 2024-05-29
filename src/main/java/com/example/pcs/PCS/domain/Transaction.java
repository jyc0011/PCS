package com.example.pcs.PCS.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyer;
    private String seller;
    private String imgHash;
    private String transactionCode;

    // Constructors, getters and setters
    public Transaction() {
    }

    public Transaction(String buyer, String seller, String imgHash, String transactionCode) {
        this.buyer = buyer;
        this.seller = seller;
        this.imgHash = imgHash;
        this.transactionCode = transactionCode;
    }

    // Additional getters and setters
}
