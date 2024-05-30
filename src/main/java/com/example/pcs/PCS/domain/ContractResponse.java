package com.example.pcs.PCS.domain;

import lombok.Data;

@Data
public class ContractResponse {
    private byte[] imageData;
    private Block block;

    public ContractResponse(byte[] imageData, Block block) {
        this.imageData = imageData;
        this.block = block;
    }
}

