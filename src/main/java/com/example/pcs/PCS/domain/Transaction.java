package com.example.pcs.PCS.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String buyer;
    private String seller;
    private String imgHash;

    // Constructors, getters and setters
    public Transaction() {
    }

    public Transaction(String buyer, String seller, String imgHash) {
        this.buyer = buyer;
        this.seller = seller;
        this.imgHash = imgHash;
    }

}
